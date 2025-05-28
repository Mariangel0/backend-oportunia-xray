package edu.backend.oportuniabackend.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID
import org.springframework.http.MediaType



@RestController
@RequestMapping("\${url.aws.curriculum}")
class AwsCvController(
    private val s3Service: S3Service,
    private val textractService: TextractService,

    @Value("\${aws.s3.bucket}")
    private val bucketName: String
) {

    @PostMapping(
        "upload/{studentId}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadCv(
        @PathVariable studentId: Long,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<Map<String, Any>> {

        val extension = file.originalFilename?.substringAfterLast('.', "") ?: "pdf"
        val key = "curriculums/$studentId/${UUID.randomUUID()}.$extension"

        val s3Url = s3Service.uploadFile(key, file)
        val sections = textractService.extractSections(bucketName, key)

        val response = mapOf(
            "s3Url" to s3Url,
            "sections" to sections
        )

        return ResponseEntity.ok(response)
    }
}