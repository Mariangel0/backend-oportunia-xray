package edu.backend.oportuniabackend.ai

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import edu.backend.oportuniabackend.Curriculum
import edu.backend.oportuniabackend.CurriculumRepository
import edu.backend.oportuniabackend.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

interface AIService {
    suspend fun chat(prompt: UserTextPrompt): ChatResponse
    suspend fun uploadCurriculum(file: MultipartFile, studentId: Long): AnalyzedCVResponse
    suspend fun generateMultipleChoiceQuiz(studentId: Long, topic: String, difficulty: String): MultipleChoiceQuestion
    suspend fun evaluateMultipleChoiceQuiz(studentId: Long, selectedOption: String): MultipleChoiceEvaluation
}

@Service
class OpenAIService(
    private val client: OpenAIClient,
    private val studentRepository: StudentRepository,
    private val curriculumRepository: CurriculumRepository
) : AIService {

    private val conversationHistories = mutableMapOf<String, MutableList<Message>>()
    private val quizHistories = mutableMapOf<Long, MultipleChoiceQuestion>()

    override suspend fun chat(prompt: UserTextPrompt): ChatResponse {
        conversationHistories.remove(prompt.id.toString())

        val userHistory = conversationHistories.getOrPut(prompt.id.toString()) {
            mutableListOf(
                Message(
                    role = "system",
                    content = AIResponseConfiguration.getInterviewConfiguration(
                        prompt.jobPosition,
                        prompt.typeOfInterview
                    )
                )
            )
        }

        userHistory.add(Message(role = "user", content = prompt.message))
        val request = ChatRequest(messages = userHistory)
        val response = client.sendPrompt(request)

        response.choices?.firstOrNull()?.message?.let {
            userHistory.add(it)
        }

        return response
    }

    suspend fun continueInterview(input: UserMessage): ChatResponse {
        val userHistory = conversationHistories[input.id.toString()]
            ?: throw IllegalStateException("No se ha iniciado una entrevista para este id: ${input.id}")

        userHistory.add(Message(role = "user", content = input.message))
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
        val promptText = """
            Eres unicamente un generador de JSON. Crea una única pregunta de selección múltiple sobre el tema "$topic" con dificultad "$difficulty".

            Devuelve ÚNICAMENTE un JSON con esta estructura:
            {
              "question": "...",
              "options": ["...", "...", "...", "..."],
              "correctAnswer": "..."
            }
        """.trimIndent()

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

        val prompt = """
            Eres un evaluador automático. Devuelve ÚNICAMENTE el siguiente JSON, sin texto adicional:
            {
              "question": "...",
              "selectedOption": "...",
              "correctAnswer": "...",
              "isCorrect": true
            }

            Pregunta: ${question.question}
            Opciones: ${question.options}
            Correcta: ${question.correctAnswer}
            Seleccionada: $selectedOption
        """.trimIndent()

        val messages = listOf(Message(role = "user", content = prompt))
        val response = client.sendPrompt(ChatRequest(messages = messages))
        val jsonString = response.choices?.firstOrNull()?.message?.content
            ?: throw IllegalStateException("No se recibió respuesta del modelo")

        return jacksonObjectMapper().readValue(jsonString)
    }
}
