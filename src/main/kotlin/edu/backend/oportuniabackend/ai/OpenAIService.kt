package edu.backend.oportuniabackend.ai

import org.springframework.stereotype.Service

@Service
class OpenAIService(
    private val client: OpenAIClient
) : AIService {
    override suspend fun chat(prompt: String): String {
        println("🧠 [SERVICE] Enviando prompt al cliente OpenAI...")
        return try {
            client.sendPrompt(prompt).also {
                println("✅ [SERVICE] Respuesta recibida exitosamente")
            }
        } catch (e: Exception) {
            println("❌ [SERVICE] Error al procesar prompt: ${e.message}")
            "Ocurrió un error al comunicarse con la IA."
        }
    }

}
