package com.example.demo.infrastructure.exception


import com.example.demo.domain.exception.AppException
import com.example.demo.presentation.dto.ApiError
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.converter.HttpMessageNotReadableException
import org.postgresql.util.PSQLException
import java.sql.SQLException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AppException::class)
    fun handleAppException(ex: AppException): ResponseEntity<ApiError> =
        ResponseEntity(ApiError(message = ex.message ?: "Application error"), ex.status)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ApiError> {
        val fieldErrors = ex.bindingResult.fieldErrors.joinToString { "${it.field}: ${it.defaultMessage}" }
        return ResponseEntity(ApiError(message = "Validation failed: $fieldErrors"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParse(ex: HttpMessageNotReadableException): ResponseEntity<ApiError> =
        ResponseEntity(ApiError(message = "Malformed JSON: ${ex.mostSpecificCause.message}"), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrity(ex: DataIntegrityViolationException): ResponseEntity<ApiError> {
        val root = ex.rootCause
        return when (root) {
            is PSQLException -> mapPostgresError(root)
            else -> ResponseEntity(ApiError(message = "Database constraint violation"), HttpStatus.CONFLICT)
        }
    }

    @ExceptionHandler(SQLException::class)
    fun handleSQL(ex: SQLException): ResponseEntity<ApiError> =
        ResponseEntity(ApiError(message = "Database error: ${ex.message}"), HttpStatus.INTERNAL_SERVER_ERROR)

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ApiError> {
        ex.printStackTrace()
        return ResponseEntity(ApiError(message = "Unexpected error: ${ex.message}"), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun mapPostgresError(ex: PSQLException): ResponseEntity<ApiError> {
        val msg = when {
            ex.message?.contains("duplicate key value") == true ->
                "Duplicate entry violates unique constraint"
            ex.message?.contains("foreign key constraint") == true ->
                "Invalid reference: foreign key violation"
            ex.message?.contains("null value in column") == true ->
                "Missing required field: null constraint violation"
            else -> "Database constraint error"
        }
        return ResponseEntity(ApiError(message = msg), HttpStatus.CONFLICT)
    }
}
