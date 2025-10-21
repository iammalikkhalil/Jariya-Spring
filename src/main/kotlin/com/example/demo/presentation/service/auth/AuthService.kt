package com.example.demo.presentation.service.auth

import com.example.demo.data.mapper.auth.toDto
import com.example.demo.data.mapper.auth.toModel
import com.example.demo.domain.enums.AuthProvider
import com.example.demo.domain.repository.auth.OtpRepository
import com.example.demo.domain.repository.auth.UserRepository
import com.example.demo.infrastructure.constants.MessageConstants
import com.example.demo.infrastructure.security.PasswordHasher
import com.example.demo.infrastructure.utils.EmailUtils
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.OtpUtils
import com.example.demo.infrastructure.utils.ReferralUtils
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.auth.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.Instant
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val otpRepository: OtpRepository,
    private val passwordHasher: PasswordHasher,
    private val emailUtils: EmailUtils
) {

    // -------------------------------------------------------------------------
    // 🟢 REGISTER USER
    // -------------------------------------------------------------------------
    fun register(request: RegisterRequest): ApiResponse<UserDto> {
        val existingUser = userRepository.findUserByEmail(request.email)
        if (existingUser != null) {
            return ApiResponse.error(HttpStatus.CONFLICT, MessageConstants.User.USER_ALREADY_EXISTS)
        }

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
            updatedAt = Instant.now()
        )

        if (!userRepository.registerUser(user.toModel())) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.User.REGISTRATION_SUCCESS)
        }

        if (!generateAndSendOtp(user.id, user.email)) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.Otp.SENT)
        }

        return ApiResponse.success(HttpStatus.CREATED, MessageConstants.User.REGISTRATION_SUCCESS, user)
    }

    // -------------------------------------------------------------------------
    // 🟡 LOGIN
    // -------------------------------------------------------------------------
    fun login(request: LoginRequest): ApiResponse<UserDto> {
        val user = userRepository.findUserByEmail(request.email)
            ?: return ApiResponse.error(HttpStatus.NOT_FOUND, MessageConstants.User.USER_NOT_FOUND)

        if (!passwordHasher.verify(request.password, user.password!!)) {
            return ApiResponse.error(HttpStatus.UNAUTHORIZED, MessageConstants.Auth.INVALID_CREDENTIALS)
        }

        if (!user.isVerified) {
            generateAndSendOtp(user.id, user.email)
            return ApiResponse.success(
                HttpStatus.FORBIDDEN,
                MessageConstants.Auth.ACCOUNT_NOT_VERIFIED,
                user.toDto()
            )
        }

        return ApiResponse.success(HttpStatus.OK, MessageConstants.Auth.LOGIN_SUCCESS, user.toDto())
    }

    // -------------------------------------------------------------------------
    // 🔁 RESEND OTP BY EMAIL
    // -------------------------------------------------------------------------
    fun resendOtpByEmail(request: ResendOtpByEmailRequest): ApiResponse<Unit> {
        val user = userRepository.findUserByEmail(request.email)
            ?: return ApiResponse.error(HttpStatus.NOT_FOUND, MessageConstants.User.USER_NOT_FOUND)

        return if (generateAndSendOtp(user.id, user.email))
            ApiResponse.success(HttpStatus.OK, MessageConstants.Otp.RESENT)
        else
            ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.Otp.INVALID)
    }

    // -------------------------------------------------------------------------
    // 🔐 VERIFY OTP BY USER ID
    // -------------------------------------------------------------------------


    fun verifyOtpByEmail(request: VerifyOtpByEmailRequest): ApiResponse<UserDto> {

        val user1 = userRepository.findUserByEmail(request.email)

        if (user1 == null)
            return ApiResponse.error(HttpStatus.NOT_FOUND, MessageConstants.Otp.INVALID)

        return verifyOtp(VerifyOtpRequest(user1.id, otp = request.otp))
    }


    fun verifyOtp(request: VerifyOtpRequest): ApiResponse<UserDto> {
        val otpRecord = otpRepository.findOtpById(request.userId)
            ?: return ApiResponse.error(HttpStatus.NOT_FOUND, MessageConstants.Otp.INVALID)

        if (otpRecord.otpAttempts >= 3) {
            return ApiResponse.error(HttpStatus.TOO_MANY_REQUESTS, MessageConstants.Otp.ATTEMPTS_EXCEEDED)
        }

        val isExpired = Instant.now().epochSecond - otpRecord.otpRequestedAt.epochSecond >= 300
        if (isExpired) {
            return ApiResponse.error(HttpStatus.REQUEST_TIMEOUT, MessageConstants.Otp.EXPIRED)
        }

        if (!OtpUtils.verifyOtp(otpRecord.toDto(), request.otp)) {
            otpRepository.incrementOtpAttempts(request.userId)
            return ApiResponse.error(HttpStatus.UNAUTHORIZED, MessageConstants.Otp.INVALID)
        }

        userRepository.markUserAsVerified(request.userId)
        otpRepository.deleteOtpById(request.userId)

        val user = userRepository.findUserById(request.userId)
            ?: return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.User.USER_NOT_FOUND)

        return ApiResponse.success(HttpStatus.OK, MessageConstants.Otp.VERIFIED, user.toDto())
    }

    // -------------------------------------------------------------------------
    // 🔄 FORGOT PASSWORD (SEND OTP)
    // -------------------------------------------------------------------------
    fun forgetPasswordSendOtp(request: ForgetPasswordSendOtpRequest): ApiResponse<UserDto> {
        val user = userRepository.findUserByEmail(request.email)
            ?: return ApiResponse.error(HttpStatus.NOT_FOUND, MessageConstants.User.USER_NOT_FOUND)

        if (!user.isVerified) {
            generateAndSendOtp(user.id, user.email)
            return ApiResponse.success(HttpStatus.FORBIDDEN, MessageConstants.Auth.ACCOUNT_NOT_VERIFIED, user.toDto())
        }

        if (user.authProvider != AuthProvider.USERNAME_PASSWORD) {
            return ApiResponse.error(
                HttpStatus.BAD_REQUEST,
                "${MessageConstants.Auth.INVALID_CREDENTIALS} (Use ${user.authProvider.value})"
            )
        }

        generateAndSendOtp(user.id, user.email)
        return ApiResponse.success(HttpStatus.OK, MessageConstants.Otp.SENT, user.toDto())
    }

    // -------------------------------------------------------------------------
    // 🔁 RESET PASSWORD
    // -------------------------------------------------------------------------
    fun resetPassword(request: ResetPasswordRequest): ApiResponse<Unit> {
        val hashedPassword = passwordHasher.hash(request.newPassword)
        val updated = userRepository.resetPassword(request.userId, hashedPassword)

        return if (updated)
            ApiResponse.success(HttpStatus.OK, MessageConstants.Password.RESET_SUCCESS)
        else
            ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.Password.RESET_FAILED)
    }

    // -------------------------------------------------------------------------
    // 👥 GET ALL USERS
    // -------------------------------------------------------------------------
    fun getAllUsers(): ApiResponse<List<UserDto>> {
        val users = userRepository.getAllUsers().map { it.toDto() }
        return ApiResponse.success(HttpStatus.OK, MessageConstants.General.SUCCESS, users)
    }

    // -------------------------------------------------------------------------
    // 🧩 HELPER: GENERATE + SEND OTP
    // -------------------------------------------------------------------------
    private fun generateAndSendOtp(userId: String, email: String): Boolean {
        val otp = OtpUtils.generateOtp(userId)
        val stored = otpRepository.generateOtp(otp.toModel())

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
    fun loginWithGoogle(request: LoginWithGoogleDto): ApiResponse<UserDto> {
        return try {
            val clientId = System.getenv("970504364234-5tkpm4t5id5sejp1vn73o4rq08m2r7sg.apps.googleusercontent.com")?.trim()
                ?: return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.Auth.MISSING_GOOGLE_CLIENT_ID)

            val verifier = GoogleIdTokenVerifier.Builder(
                NetHttpTransport(),
                GsonFactory.getDefaultInstance()
            ).setAudience(listOf(clientId)).build()

            val idToken = verifier.verify(request.token)
                ?: return ApiResponse.error(HttpStatus.UNAUTHORIZED, MessageConstants.Auth.INVALID_GOOGLE_TOKEN)

            val payload = idToken.payload
            val email = payload["email"] as? String
                ?: return ApiResponse.error(HttpStatus.BAD_REQUEST, MessageConstants.Auth.EMAIL_NOT_FOUND)

            val name = payload["name"] as? String ?: "Google User"

            val existingUser = userRepository.findUserByEmail(email)

            if (existingUser == null) {
                // new Google user
                val newUser = UserDto(
                    id = generateUUID(),
                    name = name,
                    email = email,
                    referralCode = ReferralUtils.generateReferralCode(),
                    authProvider = AuthProvider.GOOGLE,
                    isVerified = true,
                    isActive = true,
                    isDeleted = false,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now()
                )

                val registered = userRepository.registerUser(newUser.toModel())
                if (!registered) {
                    return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.User.REGISTRATION_FAILED)
                }

                return ApiResponse.success(HttpStatus.CREATED, MessageConstants.Auth.GOOGLE_REGISTER_SUCCESS, newUser)
            } else {
                if (existingUser.authProvider == AuthProvider.GOOGLE ||
                    existingUser.authProvider == AuthProvider.USERNAME_PASSWORD
                ) {
                    return ApiResponse.success(HttpStatus.OK, MessageConstants.Auth.LOGIN_SUCCESS, existingUser.toDto())
                }

                return ApiResponse.error(
                    HttpStatus.CONFLICT,
                    "${MessageConstants.Auth.ACCOUNT_EXISTS_WITH_PROVIDER} ${existingUser.authProvider}"
                )
            }
        } catch (ex: Exception) {
            Log.error("Google login failed: ${ex.message}", ex)
            ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.System.INTERNAL_ERROR)
        }
    }
}