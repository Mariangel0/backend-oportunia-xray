package edu.backend.oportuniabackend.ai

import edu.backend.oportuniabackend.ChatRequest
import edu.backend.oportuniabackend.ChatResponse
import edu.backend.oportuniabackend.Message
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
    suspend fun sendPrompt(prompt: String): String {
        val request = ChatRequest(
            model = "gpt-4-turbo",
            messages = listOf(Message(role = "user", content = prompt))
        )

        val response = webClient.post()
            .uri("/chat/completions")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $apiKey")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .retrieve()
            .awaitBody<ChatResponse>()

        return response.choices.first().message.content
    }

}


