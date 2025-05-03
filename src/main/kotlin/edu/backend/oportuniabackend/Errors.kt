package edu.backend.oportuniabackend

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

data class ApiSubError(
    val code: String? = "NO-CODE",
    val message: String? = "NO MESSAGE",
)

data class ApiError(
    val localDateTime: String = java.time.LocalDateTime.now().toString(),
    val status: HttpStatus,
    val message: String? = null,
    val debugMessage: String? = null,
    var apiSubErrors: MutableList<ApiSubError> = mutableListOf(),
) {
    fun addSubError(apiError: ApiSubError) {
        apiSubErrors.add(apiError)
    }
}

@ControllerAdvice
class OportuniaRestExceptionHandler : ResponseEntityExceptionHandler() {

    fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any> {
        return ResponseEntity(apiError, apiError.status)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun elementNotFound(
        ex: NoSuchElementException,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val apiError = ApiError(
            message = "Resource not found",
            debugMessage = ex.message,
            status = HttpStatus.NOT_FOUND,
        )

        apiError.addSubError(ApiSubError("ELEMENT_NOT_FOUND", "Requested element not found"))

        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun invalidArgument(
        ex: IllegalArgumentException,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val apiError = ApiError(
            message = "Invalid input",
            debugMessage = ex.message,
            status = HttpStatus.BAD_REQUEST,
        )

        apiError.addSubError(ApiSubError("INVALID_ARGUMENT", "The provided arguments are invalid"))

        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(
        ex: Exception,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val apiError = ApiError(
            message = "Internal server error",
            debugMessage = ex.message,
            status = HttpStatus.INTERNAL_SERVER_ERROR,
        )

        apiError.addSubError(ApiSubError("INTERNAL_ERROR", "Unexpected error occurred"))

        return buildResponseEntity(apiError)
    }
}