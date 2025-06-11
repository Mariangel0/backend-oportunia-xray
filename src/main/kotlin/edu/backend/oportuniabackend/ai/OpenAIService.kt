package edu.backend.oportuniabackend.ai

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import edu.backend.oportuniabackend.Curriculum
import edu.backend.oportuniabackend.CurriculumRepository
import edu.backend.oportuniabackend.CurriculumResult
import edu.backend.oportuniabackend.IAAnalysisInput
import edu.backend.oportuniabackend.Interview
import edu.backend.oportuniabackend.InterviewRepository
import edu.backend.oportuniabackend.InterviewResult
import edu.backend.oportuniabackend.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Date

interface AIService {
    suspend fun chat(prompt: UserTextPrompt, userId: Long): ChatResponse

    suspend fun continueInterview(studentId: Long, input: UserMessage): ChatResponse

    suspend fun uploadCurriculum(file: MultipartFile, studentId: Long): AnalyzedCVResponse

    suspend fun generateMultipleChoiceQuiz(studentId: Long, topic: String, difficulty: String): MultipleChoiceQuestion

    suspend fun evaluateMultipleChoiceQuiz(studentId: Long, selectedOption: String): MultipleChoiceEvaluation

    suspend fun generateIAAnalysis(studentId: Long, interview: InterviewResult, curriculum: CurriculumResult? = null): IAAnalysisInput
}

@Service
class OpenAIService(
    private val client: OpenAIClient,
    private val studentRepository: StudentRepository,
    private val curriculumRepository: CurriculumRepository,
    private val interviewRepository: InterviewRepository
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

    override suspend fun continueInterview(studentId: Long, input: UserMessage): ChatResponse {
        val key = studentId.toString()
        val userHistory = conversationHistories[key]
            ?: throw IllegalStateException("No se ha iniciado una entrevista para este id: $studentId")

        userHistory.add(Message(role = "user", content = input.message))

        val userMessagesCount = userHistory.count { it.role == "user" }

        if (userMessagesCount >= 5) {
            // Solo agregar mensaje de cierre si aún no se ha agregado
            if (userHistory.none { it.content.contains("esta entrevista ha concluido", ignoreCase = true) }) {
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

                interviewRepository.save(interview)

                // Opcional: limpiar conversación para evitar reutilización
                conversationHistories.remove(key)
                interviewMeta.remove(key)
            }

            return ChatResponse(choices = listOf(Choice(message = Message("assistant", AIResponseConfiguration.getInterviewClosureMessage()))))
        }

        // Si aún no termina, enviar la pregunta como respuesta
        val request = ChatRequest(messages = userHistory)
        val response = client.sendPrompt(request)

        response.choices?.firstOrNull()?.message?.let {
            userHistory.add(it)
        }

        return response
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

    override suspend fun generateIAAnalysis(studentId: Long, interview: InterviewResult, curriculum: CurriculumResult?): IAAnalysisInput {
        val history = conversationHistories[studentId.toString()]
            ?: throw IllegalStateException("No hay historial para este estudiante.")

        val transcript = history.joinToString("\n") { "${it.role}: ${it.content}" }
        val prompt = AIResponseConfiguration.getIAEvaluationPrompt(transcript)

        val response = client.sendPrompt(ChatRequest(messages = listOf(Message("user", prompt))))
        val content = response.choices?.firstOrNull()?.message?.content
            ?: throw IllegalStateException("No se recibió respuesta del modelo")

        val parsed = jacksonObjectMapper().readValue<Map<String, Any>>(content)
        val recommendation = parsed["recommendation"]?.toString() ?: "Sin recomendación"
        val score = parsed["score"]?.toString()?.toFloatOrNull() ?: 0.0f

        return IAAnalysisInput(
            recommendation = recommendation,
            score = score,
            date = Date(),
            interview = interview,
            curriculum = curriculum
        )
    }
}
