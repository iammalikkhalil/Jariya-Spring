package com.example.demo.infrastructure.exception

import com.example.demo.domain.exception.AppException
import com.example.demo.infrastructure.constants.MessageConstants
import com.example.demo.infrastructure.utils.Log
import com.example.demo.presentation.dto.ApiResponse
import org.postgresql.util.PSQLException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLException

@RestControllerAdvice
class GlobalExceptionHandler {

    // App-specific exceptions
    @ExceptionHandler(AppException::class)
    fun handleAppException(ex: AppException): ResponseEntity<ApiResponse<Nothing>> {
        Log.warn("AppException: ${ex.message}")
        return ResponseEntity(
            ApiResponse.error(ex.status, ex.message),
            ex.status
        )
    }

    // Invalid request data
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Nothing>> {
        val fieldErrors = ex.bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
        Log.warn("Validation error: $fieldErrors")
        return ResponseEntity(
            ApiResponse.error(HttpStatus.BAD_REQUEST, MessageConstants.General.INVALID_INPUT),
            HttpStatus.BAD_REQUEST
        )
    }

    // Malformed or unreadable request
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParse(ex: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Nothing>> {
        Log.warn("Malformed JSON: ${ex.mostSpecificCause.message}")
        return ResponseEntity(
            ApiResponse.error(HttpStatus.BAD_REQUEST, MessageConstants.General.INVALID_REQUEST_FORMAT),
            HttpStatus.BAD_REQUEST
        )
    }

    // Database constraint or integrity issue
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrity(ex: DataIntegrityViolationException): ResponseEntity<ApiResponse<Nothing>> {
        val root = ex.rootCause
        Log.error("Data integrity violation", ex)
        return when (root) {
            is PSQLException -> mapPostgresError(root)
            else -> ResponseEntity(
                ApiResponse.error(HttpStatus.CONFLICT, MessageConstants.Data.CONFLICT),
                HttpStatus.CONFLICT
            )
        }
    }

    // SQL-level errors
    @ExceptionHandler(SQLException::class)
    fun handleSQL(ex: SQLException): ResponseEntity<ApiResponse<Nothing>> {
        Log.error("SQL error: ${ex.message}", ex)
        return ResponseEntity(
            ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.Data.ISSUE),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    // Invalid login credentials
    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(ex: BadCredentialsException): ResponseEntity<ApiResponse<Nothing>> {
        Log.warn("Bad credentials: ${ex.message}")
        return ResponseEntity(
            ApiResponse.error(HttpStatus.UNAUTHORIZED, MessageConstants.Auth.INVALID_CREDENTIALS),
            HttpStatus.UNAUTHORIZED
        )
    }

    // Access denied
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException): ResponseEntity<ApiResponse<Nothing>> {
        Log.warn("Access denied: ${ex.message}")
        return ResponseEntity(
            ApiResponse.error(HttpStatus.FORBIDDEN, MessageConstants.General.ACCESS_DENIED),
            HttpStatus.FORBIDDEN
        )
    }

    // Catch-all for unexpected errors
    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ApiResponse<Nothing>> {
        Log.error("Unhandled exception: ${ex.message}", ex)
        return ResponseEntity(
            ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.General.UNEXPECTED_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    // PostgreSQL constraint mapping
    private fun mapPostgresError(ex: PSQLException): ResponseEntity<ApiResponse<Nothing>> {
        val msg = when {
            ex.message?.contains("duplicate key value") == true -> MessageConstants.Data.CONFLICT
            ex.message?.contains("foreign key constraint") == true -> MessageConstants.Data.FOREIGN_KEY_ERROR
            ex.message?.contains("null value in column") == true -> MessageConstants.Data.MISSING_FIELD
            else -> MessageConstants.Data.ISSUE
        }
        Log.warn("Postgres constraint violation: $msg")
        return ResponseEntity(ApiResponse.error(HttpStatus.CONFLICT, msg), HttpStatus.CONFLICT)
    }
}