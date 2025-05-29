package edu.backend.oportuniabackend.ai

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class OpenAIClient(
    private val webClient: WebClient,
    @Value("\${openai.api.key}") private val apiKey: String
) {
    suspend fun sendPrompt(prompt: ChatRequest): ChatResponse  {
        return webClient.post()
            .uri("/chat/completions")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $apiKey")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(prompt)
            .retrieve()
            .awaitBody()
    }

}


