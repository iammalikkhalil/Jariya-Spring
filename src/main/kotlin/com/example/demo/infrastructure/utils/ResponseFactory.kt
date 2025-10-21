package com.example.demo.infrastructure.utils


import com.example.demo.infrastructure.constants.MessageConstants
import com.example.demo.presentation.dto.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ResponseFactory {

    fun <T> success(
        status: HttpStatus = HttpStatus.OK,
        message: String = MessageConstants.General.SUCCESS,
        data: T? = null
    ): ResponseEntity<ApiResponse<T>> =
        ResponseEntity(ApiResponse.success(status, message, data), status)

    fun error(
        status: HttpStatus = HttpStatus.BAD_REQUEST,
        message: String = MessageConstants.General.FAILURE,
    ): ResponseEntity<ApiResponse<Nothing?>> =
        ResponseEntity(ApiResponse.error(status, message), status)
}
