package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "otps",
    indexes = [
        Index(name = "idx_otp_expires_at", columnList = "expires_at")
    ]
)
class OtpEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,   // FK → user.id (if that is your design)

    @Column(name = "hashed_otp", nullable = false, length = 100)
    var hashedOtp: String,

    @Column(name = "otp_attempts", nullable = false)
    var otpAttempts: Int = 0,

    @Column(name = "otp_requested_at", nullable = false)
    var otpRequestedAt: Instant,

    @Column(name = "expires_at", nullable = false)
    var expiresAt: Instant
)
