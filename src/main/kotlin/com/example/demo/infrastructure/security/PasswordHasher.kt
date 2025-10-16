package com.example.demo.infrastructure.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
object PasswordHasher {

    private val encoder = BCryptPasswordEncoder(12)

    fun hash(password: String): String =
        encoder.encode(password)

    fun verify(password: String, hash: String): Boolean =
        encoder.matches(password, hash)
}
