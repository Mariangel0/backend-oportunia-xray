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

    @PostMapping(
        "/{studentId}/interview",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun chat(@RequestBody request: UserTextPrompt): ChatResponse = runBlocking {
        openAIService.chat(request)
    }

    @PostMapping(
        "/{studentId}/interview/continue",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun continueChat(@RequestBody input: UserMessage): ChatResponse = runBlocking {
        openAIService.continueInterview(input)
    }

    @PostMapping("/{studentId}/curriculum", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadCurriculum(
        @RequestParam("file") file: MultipartFile,
        @PathVariable("studentId") studentId: Long
    ): ResponseEntity<AnalyzedCVResponse> {
        val result = runBlocking { openAIService.uploadCurriculum(file, studentId) }
        return ResponseEntity.ok(result)
    }


}