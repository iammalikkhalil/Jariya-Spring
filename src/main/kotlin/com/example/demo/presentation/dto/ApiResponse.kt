package com.example.demo.presentation.dto

import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val status: Int,
    val success: Boolean = true,
    val message: String,
    val data: T? = null,
) {
    companion object {
        fun <T> success(status: HttpStatus, message: String, data: T? = null): ApiResponse<T> =
            ApiResponse(status.value(), true,message, data)

        fun <T> error(status: HttpStatus, message: String, data: T? = null): ApiResponse<T> =
            ApiResponse(status.value(), false, message, data)
    }
}
