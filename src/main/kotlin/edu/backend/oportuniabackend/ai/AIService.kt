package edu.backend.oportuniabackend.ai

interface AIService {
    suspend fun chat(prompt: String): String
}
