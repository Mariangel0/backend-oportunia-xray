package edu.backend.oportuniabackend.ai

data class UserTextPrompt(
    val id: Long = 0,
    val message: String,
    val jobPosition: String,
    val typeOfInterview: String
)

data class UserMessage(
    val id: Long = 0,
    val message: String
)

data class ChatRequest(
    val model: String = "gpt-4-turbo",
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)

data class ChatResponse(
    val choices: List<Choice>?
)

data class InterviewChatResponse(
    val choices: List<Choice>?,
    val interviewId: Long? = null
)

data class Choice(
    val message: Message
)

data class AnalyzedCVResponse(
    val recomendaciones: List<String>,
    val comentarios: String,
    val score: Float
)

data class MultipleChoiceQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

data class MultipleChoiceAnswer(
    val selectedOption: String
)

data class MultipleChoiceEvaluation(
    val question: String,
    val selectedOption: String,
    val correctAnswer: String,
    val isCorrect: Boolean
)

data class MultipleChoiceGenerationRequest(
    val topic: String,
    val difficulty: String = "medium"
)