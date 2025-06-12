package edu.backend.oportuniabackend.ai

import edu.backend.oportuniabackend.IAAnalysisService
import kotlinx.coroutines.runBlocking
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("\${url.ai}")
class OpenAIController(
    private val openAIService: OpenAIService,
    private val iaAnalysisService: IAAnalysisService
) {

    @PostMapping(
        "/{studentId}/interview",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun chat(
        @PathVariable studentId: Long,
        @RequestBody request: UserTextPrompt
    ): ChatResponse = runBlocking {
        openAIService.chat(request, studentId)
    }

    @PostMapping(
        "/{studentId}/interview/continue",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun continueChat(
        @PathVariable studentId: Long,
        @RequestBody input: UserMessage
    ): InterviewChatResponse = runBlocking {
        openAIService.continueInterview(studentId, input)
    }

    @PostMapping(
        "/{studentId}/curriculum",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun uploadCurriculum(
        @RequestParam("file") file: MultipartFile,
        @PathVariable studentId: Long
    ): ResponseEntity<AnalyzedCVResponse> {
        val result = runBlocking { openAIService.uploadCurriculum(file, studentId) }
        return ResponseEntity.ok(result)
    }

    @PostMapping(
        "/{studentId}/quiz/generate",
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun generateMCQuizForUser(
        @PathVariable studentId: Long,
        @RequestBody request: MultipleChoiceGenerationRequest
    ): ResponseEntity<MultipleChoiceQuestion> = runBlocking {
        val result = openAIService.generateMultipleChoiceQuiz(studentId, request.topic, request.difficulty)
        ResponseEntity.ok(result)
    }

    @PostMapping(
        "/{studentId}/quiz/evaluate",
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun evaluateMCQuizForUser(
        @PathVariable studentId: Long,
        @RequestBody request: MultipleChoiceAnswer
    ): ResponseEntity<MultipleChoiceEvaluation> = runBlocking {
        val result = openAIService.evaluateMultipleChoiceQuiz(studentId, request.selectedOption)
        ResponseEntity.ok(result)
    }
}
