package edu.backend.oportuniabackend.ai

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import edu.backend.oportuniabackend.Curriculum
import edu.backend.oportuniabackend.CurriculumRepository
import edu.backend.oportuniabackend.CurriculumResult
import edu.backend.oportuniabackend.IAAnalysis
import edu.backend.oportuniabackend.IAAnalysisInput
import edu.backend.oportuniabackend.IAAnalysisRepository
import edu.backend.oportuniabackend.IAAnalysisService
import edu.backend.oportuniabackend.Interview
import edu.backend.oportuniabackend.InterviewRepository
import edu.backend.oportuniabackend.InterviewResult
import edu.backend.oportuniabackend.Streak
import edu.backend.oportuniabackend.StreakRepository
import edu.backend.oportuniabackend.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Calendar
import java.util.Date

interface AIService {
    suspend fun chat(prompt: UserTextPrompt, userId: Long): ChatResponse

    suspend fun continueInterview(studentId: Long, input: UserMessage): InterviewChatResponse

    suspend fun uploadCurriculum(file: MultipartFile, studentId: Long): AnalyzedCVResponse

    suspend fun generateMultipleChoiceQuiz(studentId: Long, topic: String, difficulty: String): MultipleChoiceQuestion

    suspend fun evaluateMultipleChoiceQuiz(studentId: Long, selectedOption: String): MultipleChoiceEvaluation

    suspend fun generateIAAnalysis(studentId: Long, interview: Interview): IAAnalysis
}

@Service
class OpenAIService(
    private val client: OpenAIClient,
    private val studentRepository: StudentRepository,
    private val curriculumRepository: CurriculumRepository,
    private val interviewRepository: InterviewRepository,
    private val iAAnalysisRepository: IAAnalysisRepository,
    private val streakRepository: StreakRepository,
) : AIService {

    private val conversationHistories = mutableMapOf<String, MutableList<Message>>()
    private val quizHistories = mutableMapOf<Long, MultipleChoiceQuestion>()
    private val interviewMeta = mutableMapOf<String, Pair<String, String>>() // studentId → (jobPosition, type)

    override suspend fun chat(prompt: UserTextPrompt, userId: Long): ChatResponse {
        val key = userId.toString()
        conversationHistories.remove(key)

        val systemMessage = Message(
            role = "system",
            content = AIResponseConfiguration.getInterviewPrompt(prompt.jobPosition, prompt.typeOfInterview)
        )
        val userHistory = mutableListOf(systemMessage)

        conversationHistories[key] = userHistory
        interviewMeta[key] = prompt.jobPosition to prompt.typeOfInterview

        userHistory.add(Message(role = "user", content = prompt.message))
        val response = client.sendPrompt(ChatRequest(messages = userHistory))

        response.choices?.firstOrNull()?.message?.let {
            userHistory.add(it)
        }

        return response
    }

    override suspend fun continueInterview(studentId: Long, input: UserMessage): InterviewChatResponse {
        val key = studentId.toString()
        val userHistory = conversationHistories[key]
            ?: throw IllegalStateException("No se ha iniciado una entrevista para este id: $studentId")

        userHistory.add(Message(role = "user", content = input.message))

        val userMessagesCount = userHistory.count { it.role == "user" }

        if (userMessagesCount >= 6 &&
            userHistory.none { it.content.contains("esta entrevista ha concluido", ignoreCase = true) }
        ) {
            val finalMessage = Message(
                role = "assistant",
                content = AIResponseConfiguration.getInterviewClosureMessage()
            )
            userHistory.add(finalMessage)

            val student = studentRepository.findById(studentId)
                .orElseThrow { NoSuchElementException("Estudiante con id=$studentId no encontrado") }

            val (jobPosition, type) = interviewMeta[key] ?: ("" to "")

            val interview = Interview(
                date = Date(),
                result = "Completada automáticamente",
                jobPosition = jobPosition,
                type = type,
                student = student
            )

            val savedInterview = interviewRepository.save(interview)

            val iaAnalysis = generateIAAnalysis(studentId, savedInterview)
            iAAnalysisRepository.save(iaAnalysis)

            conversationHistories.remove(key)
            interviewMeta.remove(key)

            return InterviewChatResponse(
                choices = listOf(Choice(Message("assistant", finalMessage.content))),
                interviewId = iaAnalysis.id
            )
        }

        val request = ChatRequest(messages = userHistory)
        val response = client.sendPrompt(request)

        response.choices?.firstOrNull()?.message?.let {
            userHistory.add(it)
        }

        return InterviewChatResponse(
            choices = response.choices,
            interviewId = null
        )
    }


    override suspend fun uploadCurriculum(file: MultipartFile, studentId: Long): AnalyzedCVResponse {
        val student = studentRepository.findById(studentId).orElseThrow()

        val uploadsDir = Paths.get(System.getProperty("user.dir"), "uploads", "curriculums")
        withContext(Dispatchers.IO) {
            Files.createDirectories(uploadsDir)
        }

        val safeFileName = file.originalFilename!!.replace("[^a-zA-Z0-9\\.\\-]".toRegex(), "_")
        val filePath = uploadsDir.resolve(safeFileName)
        file.transferTo(filePath.toFile())

        val doc = PDDocument.load(filePath.toFile())
        val extractedText = PDFTextStripper().getText(doc)
        doc.close()

        val promptText = """
            ${AIResponseConfiguration.getCurriculumFeedback()}
            $extractedText
        """.trimIndent()

        val messages = listOf(Message(role = "user", content = promptText))
        val response = client.sendPrompt(ChatRequest(messages = messages))

        val jsonString = response.choices?.firstOrNull()?.message?.content
            ?: throw IllegalStateException("No se recibió respuesta del modelo")

        val analyzedResponse = jacksonObjectMapper().readValue<AnalyzedCVResponse>(jsonString)

        val curriculum = Curriculum(student = student, archiveUrl = filePath.toString())
        curriculumRepository.save(curriculum)

        return analyzedResponse
    }

    override suspend fun generateMultipleChoiceQuiz(studentId: Long, topic: String, difficulty: String): MultipleChoiceQuestion {
        val promptText = AIResponseConfiguration.getQuizPrompt(topic, difficulty)
        val messages = listOf(Message(role = "user", content = promptText))
        val response = client.sendPrompt(ChatRequest(messages = messages))

        val jsonString = response.choices?.firstOrNull()?.message?.content
            ?: throw IllegalStateException("No se recibió respuesta del modelo")

        val question = jacksonObjectMapper().readValue<MultipleChoiceQuestion>(jsonString)
        quizHistories[studentId] = question

        return question
    }

    override suspend fun evaluateMultipleChoiceQuiz(studentId: Long, selectedOption: String): MultipleChoiceEvaluation {
        val question = quizHistories[studentId]
            ?: throw IllegalStateException("No hay pregunta previa para el usuario con ID: $studentId")

        val prompt = AIResponseConfiguration.getEvaluationPrompt(question, selectedOption)

        val messages = listOf(Message(role = "user", content = prompt))
        val response = client.sendPrompt(ChatRequest(messages = messages))
        val jsonString = response.choices?.firstOrNull()?.message?.content
            ?: throw IllegalStateException("No se recibió respuesta del modelo")

        return jacksonObjectMapper().readValue(jsonString)
    }

    override suspend fun generateIAAnalysis(studentId: Long, interview: Interview): IAAnalysis {
        val history = conversationHistories[studentId.toString()]
            ?: throw IllegalStateException("No hay historial para este estudiante.")

        val transcript = history.joinToString("\n") { "${it.role}: ${it.content}" }
        val prompt = AIResponseConfiguration.getIAEvaluationPrompt(transcript)

        val response = client.sendPrompt(ChatRequest(messages = listOf(Message("user", prompt))))
        val content = response.choices?.firstOrNull()?.message?.content
            ?: throw IllegalStateException("No se recibió respuesta del modelo")

        val parsed = jacksonObjectMapper().readValue<Map<String, Any>>(content)
        val recommendation = parsed["recommendation"]?.toString() ?: "Sin recomendación"
        val comment = parsed["comment"]?.toString() ?: "Sin comentario"
        val score = parsed["score"]?.toString()?.toFloatOrNull() ?: 0.0f

        return IAAnalysis(
            recommendation = recommendation,
            comment = comment,
            score = score,
            date = Date(),
            interview = interview,
        )
    }

    private fun updateOrCreateStreak(studentId: Long) {
        val student = studentRepository.findById(studentId)
            .orElseThrow { NoSuchElementException("Student with ID $studentId not found") }

        val existingStreak = streakRepository.findByStudentId(studentId)

        val today = Date()
        val calendar = Calendar.getInstance().apply { time = today }

        if (existingStreak != null) {
            val lastDate = existingStreak.lastActivity
            val lastCal = Calendar.getInstance().apply { time = lastDate }

            val diffDays = ((today.time - lastDate.time) / (1000 * 60 * 60 * 24)).toInt()

            when (diffDays) {
                0 -> return // ya se actualizó hoy
                1 -> existingStreak.days += 1 // día siguiente
                else -> existingStreak.days = 1 // se reinicia
            }

            existingStreak.lastActivity = today
            if (existingStreak.days > existingStreak.bestStreak) {
                existingStreak.bestStreak = existingStreak.days
            }

            streakRepository.save(existingStreak)
        } else {
            val newStreak = Streak(
                days = 1,
                lastActivity = today,
                bestStreak = 1,
                student = student
            )
            streakRepository.save(newStreak)
        }
    }
}