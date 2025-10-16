package com.example.demo.domain.exception

import org.springframework.http.HttpStatus

class AppException(
    val status: HttpStatus,
    override val message: String
) : RuntimeException(message)
