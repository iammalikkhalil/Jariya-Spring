package com.example.demo.infrastructure.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
object Log {
    private val logger: Logger = LoggerFactory.getLogger("AppLogger")

    fun info(message: String) = logger.info(message)
    fun warn(message: String) = logger.warn(message)
    fun error(message: String, throwable: Throwable? = null) = logger.error(message, throwable)
    fun debug(message: String) = logger.debug(message)
}
