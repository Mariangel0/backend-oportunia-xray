package edu.backend.oportuniabackend.ai

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

interface AIService {
    fun chat(prompt: String): Mono<String>
}

@Service
class OpenAIService(
    private val webClient: WebClient,
    @Value("\${openai.api.key}") private val apiKey: String
) : AIService {

    override fun chat(prompt: String): Mono<String> {
        println("API KEY = $apiKey")
        val request = mapOf(
            //"model" to "gpt-4",
            "model" to "gpt-3.5-turbo",
            "messages" to listOf(
                mapOf("role" to "user", "content" to prompt)
            )
        )

        return webClient.post()
            .header("Authorization", "Bearer $apiKey")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Map::class.java)
            .map {
                val choices = it["choices"] as List<*>
                val message = (choices[0] as Map<*, *>)["message"] as Map<*, *>
                message["content"].toString()
            }
    }
}
