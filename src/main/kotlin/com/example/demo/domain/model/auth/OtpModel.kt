package com.example.demo.domain.model.auth

import java.time.Instant

data class OtpModel(
    val id: String,
    val hashedOtp: String,
    val otp: String,
    val otpRequestedAt: Instant,
    val expiresAt: Instant,
    val otpAttempts: Int = 0
)
