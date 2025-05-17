package edu.backend.oportuniabackend.ai

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("\${url.ai}")
class OpenAIController(
    private val openAIService: OpenAIService
) {

    @PostMapping(
        path = ["/ask"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseBody
    fun ask(@RequestBody request: PromptRequest): Mono<String> {
        return openAIService.chat(request.prompt)
    }

    data class PromptRequest(val prompt: String)
}
