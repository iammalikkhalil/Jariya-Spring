package com.example.demo.presentation.service.auth


import com.example.demo.domain.enums.AuthProvider
import com.example.demo.infrastructure.security.PasswordHasher
import com.example.demo.infrastructure.utils.EmailUtils
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.OtpUtils
import com.example.demo.infrastructure.utils.ReferralUtils
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.auth.*
import com.example.demo.utils.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AuthService(
//    private val userRepository: UserRepository,
//    private val otpRepository: OtpRepository,
    private val passwordHasher: PasswordHasher,
    private val emailUtils: EmailUtils
) {

    fun register(request: RegisterRequest): ApiResponse<UserDto> {
        // 1. Check existing user
        if (userRepository.findUserByEmail(request.email) != null) {
            return ApiResponse.error(HttpStatus.CONFLICT, "User already exists")
        }

        // 2. Create new user
        val user = UserDto(
            id = generateUUID(),
            name = request.name,
            email = request.email,
            password = passwordHasher.hash(request.password),
            profileImage = request.profileImage,
            referredBy = null,
            referralCode = ReferralUtils.generateReferralCode(),
            authProvider = AuthProvider.USERNAME_PASSWORD,
            isVerified = false,
            isDeleted = false,
            isActive = true,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
        )

        // 3. Save to DB
        if (!userRepository.registerUser(user)) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Registration failed")
        }

        // 4. Generate OTP and send
        if (!generateAndSendOtp(user.id, user.email)) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send OTP")
        }

        return ApiResponse.success(HttpStatus.CREATED, "User registered! OTP sent to email.", user)
    }

    fun login(request: LoginRequest): ApiResponse<UserDto> {
        val user = userRepository.findUserByEmail(request.email)
            ?: return ApiResponse.error(HttpStatus.NOT_FOUND, "User not found")

        if (!passwordHasher.verify(request.password, user.password!!)) {
            return ApiResponse.error(HttpStatus.UNAUTHORIZED, "Invalid credentials")
        }

        if (!user.isVerified) {
            generateAndSendOtp(user.id, user.email)
            return ApiResponse.success(HttpStatus.FORBIDDEN, "Account not verified. OTP sent to your email.", user)
        }

//        val token = tokenService.generateToken(user.id, user.email)
//        return ApiResponse.success(HttpStatus.OK, "Login successfully", user.copy(token = token))
        return ApiResponse.success(HttpStatus.OK, "Login successfully", user)
    }

    fun resendOtpByEmail(request: ResendOtpByEmailRequest): ApiResponse<Unit> {
        val user = userRepository.findUserByEmail(request.email)
            ?: return ApiResponse.error(HttpStatus.NOT_FOUND, "User not found")

        return if (generateAndSendOtp(user.id, user.email))
            ApiResponse.success(HttpStatus.OK, "New OTP sent to your email")
        else
            ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send OTP")
    }

    fun verifyOtp(request: VerifyOtpRequest): ApiResponse<UserDto> {
        val otpRecord = otpRepository.findOtpById(request.userId)
            ?: return ApiResponse.error(HttpStatus.NOT_FOUND, "OTP not found")

        if (otpRecord.otpAttempts >= 3) {
            return ApiResponse.error(HttpStatus.TOO_MANY_REQUESTS, "Too many incorrect attempts. Please request a new OTP.")
        }

        val secondsSinceRequest = Instant.now().epochSecond - otpRecord.otpRequestedAt.epochSecond
        if (secondsSinceRequest >= 300) {
            return ApiResponse.error(HttpStatus.REQUEST_TIMEOUT, "OTP expired. Please request a new one.")
        }

        if (!OtpUtils.verifyOtp(otpRecord, request.otp)) {
            otpRepository.incrementOtpAttempts(request.userId)
            return ApiResponse.error(HttpStatus.UNAUTHORIZED, "Invalid OTP")
        }

        // ✅ Valid OTP
        userRepository.markUserAsVerified(request.userId)
        otpRepository.deleteOtpById(request.userId)

        val user = userRepository.findUserById(request.userId)
            ?: return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "User not found after verification")

        val token = tokenService.generateToken(user.id, user.email)
        return ApiResponse.success(HttpStatus.OK, "OTP verified successfully!", user.copy(password = "", token = token))
    }

    fun forgetPasswordSendOtp(request: ForgetPasswordSendOtpRequest): ApiResponse<UserDto> {
        val user = userRepository.findUserByEmail(request.email)
            ?: return ApiResponse.error(HttpStatus.NOT_FOUND, "User not found")

        if (!user.isVerified) {
            generateAndSendOtp(user.id, user.email)
            return ApiResponse.success(HttpStatus.FORBIDDEN, "Account not verified. OTP sent to your email.", user)
        }

        if (user.authProvider != AuthProvider.USERNAME_PASSWORD) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, "Please try login with ${user.authProvider.value}")
        }

        generateAndSendOtp(user.id, user.email)
        return ApiResponse.success(HttpStatus.OK, "OTP sent to your email.", user)
    }

    fun resetPassword(request: ResetPasswordRequest): ApiResponse<Unit> {
        val hashedPassword = passwordHasher.hash(request.newPassword)
        val updated = userRepository.resetPassword(request.userId, hashedPassword)

        return if (updated)
            ApiResponse.success(HttpStatus.OK, "Password reset successfully")
        else
            ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error resetting password")
    }

    fun getAllUsers(): ApiResponse<List<UserDto>> {
        val users = userRepository.getAllUsers()
        return ApiResponse.success(HttpStatus.OK, "Fetched all users", users)
    }

    // -----------------------------------------
    // 🔐 Helper: Generate and Send OTP
    // -----------------------------------------
    private fun generateAndSendOtp(userId: String, email: String): Boolean {
        val otp = OtpUtils.generateOtp(userId)
        val stored = otpRepository.generateOtp(otp)

        if (!stored) {
            Log.error("Failed to store OTP for $email")
            return false
        }

        return try {
            emailUtils.sendOtpEmail(email, otp.otp)
            Log.info("OTP sent successfully to $email")
            true
        } catch (e: Exception) {
            Log.error("Failed to send OTP email: ${e.message}", e)
            false
        }
    }
}
