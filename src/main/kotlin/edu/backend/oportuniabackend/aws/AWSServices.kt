package edu.backend.oportuniabackend.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ObjectCannedACL
import software.amazon.awssdk.services.s3.model.PutObjectRequest
//import software.amazon.awssdk.services.s3.model.S3Object
import software.amazon.awssdk.services.textract.TextractClient
import software.amazon.awssdk.services.textract.model.AnalyzeDocumentRequest
import software.amazon.awssdk.services.textract.model.Block
import software.amazon.awssdk.services.textract.model.DetectDocumentTextRequest
import software.amazon.awssdk.services.textract.model.Document
import software.amazon.awssdk.services.textract.model.BlockType
import software.amazon.awssdk.services.textract.model.FeatureType
import software.amazon.awssdk.services.textract.model.GetDocumentAnalysisRequest
import software.amazon.awssdk.services.textract.model.JobStatus
import software.amazon.awssdk.services.textract.model.S3Object
import software.amazon.awssdk.services.textract.model.StartDocumentAnalysisRequest


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
class TextractService(private val textractClient: TextractClient) {

    @Throws(RuntimeException::class)
    fun extractSections(bucket: String, key: String): Map<String, String> {
        val blocks = analyzeMultiPageDocument(bucket, key)

        val lines = blocks
            .filter { it.blockType() == BlockType.LINE && !it.text().isNullOrBlank() }
            .map { it.text()!! }

        val headers = listOf(
            "CONTACTO",
            "OBJETIVO PROFESIONAL",
            "EXPERIENCIA LABORAL",
            "EDUCACIÓN",
            "HABILIDADES",
            "IDIOMAS",
            "REFERENCIAS"
        )

        val sections = headers.associateWith { extractSection(it, lines) }

        return sections
    }

    private fun analyzeMultiPageDocument(bucket: String, key: String): List<Block> {

        val startRequest = StartDocumentAnalysisRequest.builder()
            .documentLocation { it.s3Object { s3 -> s3.bucket(bucket).name(key).build() } }
            .featureTypes(FeatureType.FORMS, FeatureType.TABLES)
            .build()

        val startResponse = textractClient.startDocumentAnalysis(startRequest)
        val jobId = startResponse.jobId()

        val blocks = mutableListOf<Block>()
        var jobStatus: JobStatus
        var nextToken: String? = null

        do {
            Thread.sleep(5000)
            val getRequestBuilder = GetDocumentAnalysisRequest.builder()
                .jobId(jobId)
                .maxResults(1000)

            if (nextToken != null) {
                getRequestBuilder.nextToken(nextToken)
            }

            val getResponse = textractClient.getDocumentAnalysis(getRequestBuilder.build())
            jobStatus = getResponse.jobStatus()
            blocks.addAll(getResponse.blocks())

            nextToken = getResponse.nextToken()
        } while (jobStatus == JobStatus.IN_PROGRESS || nextToken != null)

        if (jobStatus != JobStatus.SUCCEEDED) {
            throw RuntimeException("Textract analysis failed with status $jobStatus")
        }

        return blocks
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
}
