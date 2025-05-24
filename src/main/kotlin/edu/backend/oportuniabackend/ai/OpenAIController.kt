package edu.backend.oportuniabackend.ai

import kotlinx.coroutines.runBlocking
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("\${url.ai}")
class OpenAIController(
    private val openAIService: OpenAIService
) {
    data class PromptRequest(val prompt: String)

    @PostMapping(
        "/ask",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun chat(@RequestBody request: PromptRequest): String {
        return  runBlocking { openAIService.chat(request.prompt)  }
    }

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadCurriculum(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("studentId") studentId: Long
    ): ResponseEntity<String> {
        val result = runBlocking { openAIService.uploadCurriculum(file, studentId) }
        return ResponseEntity.ok(result)
    }


}