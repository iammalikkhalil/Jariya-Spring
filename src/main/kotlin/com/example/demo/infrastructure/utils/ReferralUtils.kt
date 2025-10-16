package com.example.demo.infrastructure.utils


import org.springframework.stereotype.Component

@Component
object ReferralUtils {

    fun generateReferralCode(
        length: Int = 14,
        useUpperCase: Boolean = true,
        useNumbers: Boolean = true
    ): String {
        val letters = ('a'..'z') + if (useUpperCase) ('A'..'Z') else emptyList()
        val digits = if (useNumbers) ('0'..'9') else emptyList()
        val allowedChars = letters + digits

        return List(length) { allowedChars.random() }.joinToString("")
    }
}
