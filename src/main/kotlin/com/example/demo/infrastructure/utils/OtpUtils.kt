package com.example.demo.infrastructure.utils


import com.example.demo.infrastructure.security.PasswordHasher
import com.example.demo.presentation.dto.auth.OtpDto
import java.time.Instant
import java.time.temporal.ChronoUnit
import org.springframework.stereotype.Component

@Component
object OtpUtils {

    // Generate OTP for the given userId
    fun generateOtp(userId: String): OtpDto {
        val otp = generateRandomOtp()
        val hashedOtp = hashOtp(otp)
        val otpRequestedAt = Instant.now()
        val expiresAt = otpRequestedAt.plus(10, ChronoUnit.MINUTES)

        return OtpDto(
            id = userId,
            hashedOtp = hashedOtp,
            otpRequestedAt = otpRequestedAt,
            expiresAt = expiresAt,
            otp = otp
        )
    }

    // Verify OTP: Check expiry and correctness
    fun verifyOtp(storedOtp: OtpDto, inputOtp: String): Boolean {
        if (Instant.now().isAfter(storedOtp.expiresAt)) {
            Log.warn("OTP expired for user: ${storedOtp.id}")
            return false
        }

        if (storedOtp.otpAttempts >= 3) {
            Log.warn("Max OTP attempts reached for user: ${storedOtp.id}")
            return false
        }

        return verifyHashedOtp(inputOtp, storedOtp.hashedOtp)
    }

    fun generateRandomOtp(): String =
        (100000..999999).random().toString()

    fun hashOtp(otp: String): String =
        PasswordHasher.hash(otp)

    fun verifyHashedOtp(inputOtp: String, storedHash: String): Boolean =
        PasswordHasher.verify(inputOtp, storedHash)
}
