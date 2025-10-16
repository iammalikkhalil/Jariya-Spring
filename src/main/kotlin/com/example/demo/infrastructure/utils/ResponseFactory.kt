package com.example.demo.infrastructure.utils


import com.example.demo.presentation.dto.ApiError
import com.example.demo.presentation.dto.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ResponseFactory {

    fun <T> success(
        status: HttpStatus = HttpStatus.OK,
        message: String = "Success",
        data: T? = null
    ): ResponseEntity<ApiResponse<T>> =
        ResponseEntity(ApiResponse(true, message, data), status)

    fun error(
        status: HttpStatus = HttpStatus.BAD_REQUEST,
        message: String = "Failed"
    ): ResponseEntity<ApiError> =
        ResponseEntity(ApiError(false, message), status)
}
