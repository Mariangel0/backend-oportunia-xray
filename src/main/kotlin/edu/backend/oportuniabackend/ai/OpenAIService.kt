package edu.backend.oportuniabackend.ai

import edu.backend.oportuniabackend.Curriculum
import edu.backend.oportuniabackend.CurriculumRepository
import edu.backend.oportuniabackend.StudentRepository
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

interface AIService {
    suspend fun chat(prompt: String): String
    suspend fun uploadCurriculum(file: MultipartFile, studentId: Long): String
}

@Service
class OpenAIService(
    private val client: OpenAIClient,
    private val studentRepository: StudentRepository,
    private val curriculumRepository: CurriculumRepository
) : AIService {
    override suspend fun chat(prompt: String): String {
        return client.sendPrompt(prompt)
    }

    override suspend fun uploadCurriculum(file: MultipartFile, studentId: Long): String {
        val student = studentRepository.findById(studentId).orElseThrow()

        val uploadsDir = Paths.get(System.getProperty("user.dir"), "uploads", "curriculums")
        Files.createDirectories(uploadsDir)

        val safeFileName = file.originalFilename!!
            .replace("[^a-zA-Z0-9\\.\\-]".toRegex(), "_")
        val filePath = uploadsDir.resolve(safeFileName)

        file.transferTo(filePath.toFile())

        val doc = PDDocument.load(filePath.toFile())
        val stripper = PDFTextStripper()
        val extractedText = stripper.getText(doc)
        doc.close()

        val prompt = """
            Este es un currículum de un estudiante. Revísalo y proporciona recomendaciones para mejorar:
            $extractedText
        """.trimIndent()

        val feedback = client.sendPrompt(prompt)

        val curriculum = Curriculum(
            student = student,
            archiveUrl = filePath.toString(),
            feedback = feedback
        )
        curriculumRepository.save(curriculum)

        return """
        Currículum analizado con éxito.

        Recomendaciones:
            $feedback
        """.trimIndent()
    }

}