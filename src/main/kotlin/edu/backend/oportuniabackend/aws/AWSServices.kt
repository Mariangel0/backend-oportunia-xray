package edu.backend.oportuniabackend.aws

import edu.backend.oportuniabackend.AbilityInput
import edu.backend.oportuniabackend.AbilityService
import edu.backend.oportuniabackend.EducationInput
import edu.backend.oportuniabackend.EducationService
import edu.backend.oportuniabackend.ExperienceInput
import edu.backend.oportuniabackend.ExperienceService
import edu.backend.oportuniabackend.StudentInput
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.textract.TextractClient
import software.amazon.awssdk.services.textract.model.Block
import software.amazon.awssdk.services.textract.model.BlockType
import software.amazon.awssdk.services.textract.model.DetectDocumentTextRequest
import software.amazon.awssdk.services.textract.model.Document
import software.amazon.awssdk.services.textract.model.S3Object

@Service
class S3Service(
    private val s3Client: S3Client,
    @Value("\${aws.s3.bucket}")
    private val bucketName: String
) {
    fun uploadFile(key: String, file: MultipartFile): String {
        val putRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()

        s3Client.putObject(putRequest, RequestBody.fromBytes(file.bytes))

        return "https://$bucketName.s3.amazonaws.com/$key"
    }
}

@Service
class TextractClientService(private val textractClient: TextractClient) {

    fun analyzeDocumentBasic(bucket: String, key: String): List<Block> {
        val request = DetectDocumentTextRequest.builder()
            .document(
                Document.builder()
                    .s3Object(
                        S3Object.builder()
                            .bucket(bucket)
                            .name(key)
                            .build()
                    )
                    .build()
            )
            .build()

        val response = textractClient.detectDocumentText(request)

        return response.blocks()
    }
}

@Service
class TextractProcessingService(
    private val textractClientService: TextractClientService,
    private val educationService: EducationService,
    private val experienceService: ExperienceService,
    private val abilityService: AbilityService
) {
    fun processCv(bucket: String, key: String, studentId: Long): Map<String, String> {
        val blocks = textractClientService.analyzeDocumentBasic(bucket, key)

        val lines = extractLines(blocks)
        val sections = extractSections(lines)

        val experienceText = sections["EXPERIENCIA LABORAL"] ?: ""
        val educationText = sections["EDUCACIÓN"] ?: ""
        val abilitiesText = sections["HABILIDADES"] ?: ""

        saveExperiencesToDB(experienceText, studentId)
        saveEducationToDB(educationText, studentId)
        saveAbilitiesToDB(abilitiesText, studentId)

        return sections
    }

    private fun extractLines(blocks: List<Block>): List<String> =
        blocks.filter { it.blockType() == BlockType.LINE && !it.text().isNullOrBlank() }
            .map { it.text()!! }

    private fun extractSections(lines: List<String>): Map<String, String> {
        val headers = listOf(
            "CONTACTO",
            "OBJETIVO PROFESIONAL",
            "EXPERIENCIA LABORAL",
            "EDUCACIÓN",
            "HABILIDADES",
            "IDIOMAS",
            "REFERENCIAS"
        )

        return headers.associateWith { header -> extractSection(header, lines) }
    }

    private fun extractSection(header: String, lines: List<String>): String {
        val start = lines.indexOfFirst { it.trim().equals(header, ignoreCase = true) }
        if (start == -1) return ""
        val end = lines.drop(start + 1).indexOfFirst {
            val line = it.trim()
            line == line.uppercase() && line.length in 3..40 && line != header
        }
        val limit = if (end == -1) lines.size else start + 1 + end
        return lines.subList(start + 1, limit).joinToString("\n")
    }

    private fun saveEducationToDB(educationText: String, studentId: Long) {
        val educationDetails = parseEducationDetails(educationText)
        educationDetails.forEach {
            val educationInput = EducationInput(
                student = StudentInput(id = studentId),
                name = it.name,
                institution = it.institution,
                year = it.year
            )
            educationService.create(educationInput)
        }
    }

    private fun saveExperiencesToDB(experienceText: String, studentId: Long) {
        val experienceDetails = parseExperienceDetails(experienceText)
        experienceDetails.forEach {
            val experienceInput = ExperienceInput(
                student = StudentInput(id = studentId),
                role = it.role,
                company = it.company,
                timeline = it.timeline
            )
            experienceService.create(experienceInput)
        }
    }

    private fun saveAbilitiesToDB(abilitiesText: String, studentId: Long) {
        val abilitiesDetails = parseAbilities(abilitiesText)
        abilitiesDetails.forEach {
            val abilityInput = AbilityInput(
                student = StudentInput(id = studentId),
                name = it.name
            )
            abilityService.create(abilityInput)
        }
    }

    private fun parseExperienceDetails(experienceText: String): List<ExperienceDetail> {
        val result = mutableListOf<ExperienceDetail>()
        val lines = experienceText.lines()
        val regex = """(.*?)\s*[-|]\s*(.*?)\s*[-|]\s*(\d{4}[\–\-]?\d{0,4}[–\s]?(?:Presente|[0-9]{4}))""".toRegex()

        for (line in lines) {
            val matchResult = regex.find(line)
            if (matchResult != null) {
                val role = matchResult.groupValues[1].trim()
                val company = matchResult.groupValues[2].trim()
                val timeline = matchResult.groupValues[3].trim()
                result.add(ExperienceDetail(role = role, company = company, timeline = timeline))
            } else {
                println("No match for line: $line")
            }
        }
        return result
    }

    private fun parseEducationDetails(educationText: String): List<EducationDetail> {
        val result = mutableListOf<EducationDetail>()
        val lines = educationText.lines()
        for (line in lines) {
            val yearRegex = Regex("""\b(19|20)\d{2}\b""")
            val yearMatch = yearRegex.find(line)
            val year = yearMatch?.value?.toIntOrNull() ?: 0

            val withoutYear = line.replace(yearRegex, "").trim()
            val parts = withoutYear.split('-')
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            val institution = parts.getOrNull(0) ?: "Unknown"
            val name = parts.getOrNull(1) ?: "Unknown"

            result.add(EducationDetail(institution = institution, name = name, year = year))
        }
        return result
    }

    private fun parseAbilities(abilitiesText: String): List<AbilityDetail> {
        val result = mutableListOf<AbilityDetail>()
        val lines = abilitiesText.lines()
        for (line in lines) {
            if (line.startsWith("-")) {
                val abilityName = line.trimStart('-').trim()
                result.add(AbilityDetail(name = abilityName))
            }
        }
        return result
    }
}

data class ExperienceDetail(val role: String, val company: String, val timeline: String)
data class EducationDetail(val institution: String, val name: String, val year: Int)
data class AbilityDetail(val name: String)
