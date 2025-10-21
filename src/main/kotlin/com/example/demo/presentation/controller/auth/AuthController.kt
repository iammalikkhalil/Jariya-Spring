package com.example.demo.presentation.controller.auth

import com.example.demo.infrastructure.constants.MessageConstants
import com.example.demo.infrastructure.utils.ResponseFactory
import com.example.demo.presentation.dto.auth.*
import com.example.demo.presentation.service.auth.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    // -------------------------------------------------------------------------
    // 🟢 REGISTER
    // -------------------------------------------------------------------------
    @PostMapping("/register")
    fun register(@RequestBody body: RegisterRequest): ResponseEntity<*> {
        val response = authService.register(body)
        return ResponseEntity(response, HttpStatus.valueOf(response.status))
    }

    // -------------------------------------------------------------------------
    // 🟡 LOGIN
    // -------------------------------------------------------------------------
    @PostMapping("/login")
    fun login(@RequestBody body: LoginRequest): ResponseEntity<*> {
        val response = authService.login(body)
        return ResponseEntity(response, HttpStatus.valueOf(response.status))
    }

    // -------------------------------------------------------------------------
    // 🔁 RESEND OTP BY EMAIL
    // -------------------------------------------------------------------------
    @PostMapping("/resendOtpByEmail")
    fun resendOtpByEmail(@RequestBody body: ResendOtpByEmailRequest): ResponseEntity<*> {
        val response = authService.resendOtpByEmail(body)
        return ResponseEntity(response, HttpStatus.valueOf(response.status))
    }


    // -------------------------------------------------------------------------
    // 🔐 VERIFY OTP (BY USER ID)
    // -------------------------------------------------------------------------
    @PostMapping("/verifyOtp")
    fun verifyOtp(@RequestBody body: VerifyOtpRequest): ResponseEntity<*> {
        val response = authService.verifyOtp(body)
        return ResponseEntity(response, HttpStatus.valueOf(response.status))
    }

    @PostMapping("/verifyOtpByEmail")
    fun verifyOtp(@RequestBody body: VerifyOtpByEmailRequest): ResponseEntity<*> {
        val response = authService.verifyOtpByEmail(body)
        return ResponseEntity(response, HttpStatus.valueOf(response.status))
    }

    // -------------------------------------------------------------------------
    // 🔄 FORGOT PASSWORD (SEND OTP)
    // -------------------------------------------------------------------------
    @PostMapping("/forgetPasswordSendOtp")
    fun forgetPasswordSendOtp(@RequestBody body: ForgetPasswordSendOtpRequest): ResponseEntity<*> {
        val response = authService.forgetPasswordSendOtp(body)
        return ResponseEntity(response, HttpStatus.valueOf(response.status))
    }

    // -------------------------------------------------------------------------
    // 🔁 RESET PASSWORD
    // -------------------------------------------------------------------------
    @PostMapping("/resetPassword")
    fun resetPassword(@RequestBody body: ResetPasswordRequest): ResponseEntity<*> {
        val response = authService.resetPassword(body)
        return ResponseEntity(response, HttpStatus.valueOf(response.status))
    }

    // -------------------------------------------------------------------------
    // 👥 GET ALL USERS
    // -------------------------------------------------------------------------
    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<*> {
        val response = authService.getAllUsers()
        return ResponseEntity(response, HttpStatus.valueOf(response.status))
    }

    // -------------------------------------------------------------------------
    // 🧩 SOCIAL LOGIN
    // -------------------------------------------------------------------------

    @PostMapping("/loginWithFacebook")
    fun loginWithFacebook(@RequestBody body: LoginWithFacebookDto): ResponseEntity<*> {
        // You can later integrate Facebook OAuth here
        return ResponseFactory.error(
            HttpStatus.NOT_IMPLEMENTED,
            MessageConstants.System.FEATURE_UNAVAILABLE
        )
    }

    @PostMapping("/loginWithGoogle")
    fun loginWithGoogle(@RequestBody body: LoginWithGoogleDto): ResponseEntity<*> {
        val response = authService.loginWithGoogle(body)
        return ResponseEntity(response, HttpStatus.valueOf(response.status))
    }
}