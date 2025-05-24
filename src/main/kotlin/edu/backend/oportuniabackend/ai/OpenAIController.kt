package edu.backend.oportuniabackend.ai

import kotlinx.coroutines.runBlocking
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

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
    suspend fun chat(@RequestBody request: PromptRequest): String {
        println("🎯 [CONTROLLER] Prompt recibido: ${request.prompt}")
        return runBlocking {  openAIService.chat(request.prompt) }
    }

}
