package com.example.demo.presentation.dto.auth

import com.example.demo.domain.enums.AuthProvider
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant


@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val password: String? = null,
    val profileImage: String? = null,
    val referredBy: String? = null,
    val referralCode: String,
    val authProvider: AuthProvider = AuthProvider.USERNAME_PASSWORD,
    val token: String? = null,
    val isVerified: Boolean = false,
    val isDeleted: Boolean = false,
    val isActive: Boolean = true,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null
)


data class RegisterRequest(
    val id: String? = null,
    val name: String,
    val email: String,
    val password: String,
    val authProvider: String = AuthProvider.USERNAME_PASSWORD.value,
    val referralCodeUsed: String? = null,
    val profileImage: String? = null
)


data class LoginRequest(
    val email: String,
    val password: String
)


data class LoginWithGoogleDto(
    val token: String
)


data class LoginWithFacebookDto(
    val token: String
)


data class FacebookUserDto(
    val id: String,
    val name: String,
    val email: String? = null
)


data class ResendOtpRequest(
    val userId: String
)


data class ResendOtpByEmailRequest(
    val email: String
)


data class ForgetPasswordSendOtpRequest(
    val email: String
)


data class ResetPasswordRequest(
    val userId: String,
    val newPassword: String
)


data class VerifyOtpRequest(
    val userId: String,
    val otp: String
)


data class VerifyOtpByEmailRequest(
    val email: String,
    val otp: String
)

data class OtpDto(
    val id: String,             // FK to User.id — links OTP to specific user (enforces one-to-one)
    val hashedOtp: String,          // Store only the hashed OTP for security
    val otp: String,          // Store OTP for Email
    val otpRequestedAt: Instant,    // Timestamp of OTP creation (used for expiry logic and rate limiting)
    val expiresAt: Instant,         // OTP expiry timestamp (e.g., valid for 10 minutes)
    val otpAttempts: Int = 0        // Track how many times user tried verifying the OTP (max attempt logic)
)