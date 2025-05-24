package edu.backend.oportuniabackend.ai

import org.springframework.stereotype.Service

interface AIService {
    suspend fun chat(prompt: String): String
}

@Service
class OpenAIService(
    private val client: OpenAIClient
) : AIService {
    override suspend fun chat(prompt: String): String {
        return client.sendPrompt(prompt)
    }

}