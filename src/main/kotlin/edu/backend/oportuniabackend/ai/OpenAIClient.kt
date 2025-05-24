package edu.backend.oportuniabackend.ai

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToMono

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

        println("📤 [CLIENT] Preparando solicitud con modelo: ${request.model}")
        println("📝 [CLIENT] Prompt: $prompt")

        val response = webClient.post()
            .uri("/chat/completions")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $apiKey")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .retrieve()
            .awaitBody<ChatResponse>().also {
                println("📥 [CLIENT] Respuesta de OpenAI recibida: ${it.choices.firstOrNull()?.message?.content}")
            }

        return response.choices.first().message.content
    }

}


data class ChatRequest(
    val model: String,
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)

data class ChatResponse(
    val id: String,
    val choices: List<Choice>
)

data class Choice(
    val index: Int,
    val message: Message,
    val finish_reason: String
)

