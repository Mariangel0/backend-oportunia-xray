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
}

@Service
class OpenAIService(
    private val client: OpenAIClient,
    private val studentRepository: StudentRepository,
    private val curriculumRepository: CurriculumRepository
) : AIService {

    private val conversationHistories = mutableMapOf<String, MutableList<Message>>()


    override suspend fun chat(prompt: UserTextPrompt): ChatResponse {
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

        val safeFileName = file.originalFilename!!
            .replace("[^a-zA-Z0-9\\.\\-]".toRegex(), "_")
        val filePath = uploadsDir.resolve(safeFileName)

        file.transferTo(filePath.toFile())

        val doc = PDDocument.load(filePath.toFile())
        val stripper = PDFTextStripper()
        val extractedText = stripper.getText(doc)
        doc.close()

        val promptText = """
        ${AIResponseConfiguration.getCurriculumFeedback()}
        $extractedText
    """.trimIndent()

        val messages = listOf(
            Message(role = "user", content = promptText)
        )

        val chatRequest = ChatRequest(messages = messages)

        val response = client.sendPrompt(chatRequest)

        val jsonString = response.choices?.firstOrNull()?.message?.content
            ?: throw IllegalStateException("No se recibió respuesta del modelo")

        val mapper = jacksonObjectMapper()
        val analyzedResponse = mapper.readValue<AnalyzedCVResponse>(jsonString)

        val curriculum = Curriculum(
            student = student,
            archiveUrl = filePath.toString(),
            feedback = jsonString
        )
        curriculumRepository.save(curriculum)

        return analyzedResponse
    }

}