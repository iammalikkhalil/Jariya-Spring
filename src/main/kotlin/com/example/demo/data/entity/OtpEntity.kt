package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "otps")
data class OtpEntity(

    @Id
    @Column(nullable = false, unique = true)
    val id: UUID, // FK to user

    @Column(name = "hashed_otp", nullable = false, length = 100)
    val hashedOtp: String,

    @Column(name = "otp_attempts", nullable = false)
    var otpAttempts: Int = 0,

    @Column(name = "otp_requested_at", nullable = false)
    val otpRequestedAt: Instant,

    @Column(name = "expires_at", nullable = false)
    val expiresAt: Instant
)
