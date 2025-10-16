package com.example.demo.presentation.controller.auth

import com.example.demo.infrastructure.utils.ResponseFactory
import com.example.demo.presentation.dto.auth.ForgetPasswordSendOtpRequest
import com.example.demo.presentation.dto.auth.LoginRequest
import com.example.demo.presentation.dto.auth.LoginWithFacebookDto
import com.example.demo.presentation.dto.auth.LoginWithGoogleDto
import com.example.demo.presentation.dto.auth.RegisterRequest
import com.example.demo.presentation.dto.auth.ResendOtpByEmailRequest
import com.example.demo.presentation.dto.auth.ResendOtpRequest
import com.example.demo.presentation.dto.auth.ResetPasswordRequest
import com.example.demo.presentation.dto.auth.VerifyOtpByEmailRequest
import com.example.demo.presentation.dto.auth.VerifyOtpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    // 🔹 Register new user
    @PostMapping("/register")
    fun register(@RequestBody body: RegisterRequest): ResponseEntity<*> {
        val user = authService.register(body)
        return ResponseFactory.success(HttpStatus.CREATED, "User registered! OTP sent to email.", user)
    }

    // 🔹 Login with email/password
    @PostMapping("/login")
    fun login(@RequestBody body: LoginRequest): ResponseEntity<*> {
        val user = authService.login(body)
        return ResponseFactory.success(HttpStatus.OK, "Login successfully", user)
    }

    // 🔹 Login with Google
    @PostMapping("/login/google")
    fun loginWithGoogle(@RequestBody body: LoginWithGoogleDto): ResponseEntity<*> {
        val user = authService.loginWithGoogle(body)
        return ResponseFactory.success(HttpStatus.OK, "User logged in successfully", user)
    }

    // 🔹 Login with Facebook
    @PostMapping("/login/facebook")
    fun loginWithFacebook(@RequestBody body: LoginWithFacebookDto): ResponseEntity<*> {
        val user = authService.loginWithFacebook(body)
        return ResponseFactory.success(HttpStatus.OK, "User logged in successfully", user)
    }

    // 🔹 Verify OTP by user ID
    @PostMapping("/verify-otp")
    fun verifyOtp(@RequestBody body: VerifyOtpRequest): ResponseEntity<*> {
        val user = authService.verifyOtp(body)
        return ResponseFactory.success(HttpStatus.OK, "OTP verified successfully", user)
    }

    // 🔹 Verify OTP by email
    @PostMapping("/verify-otp/email")
    fun verifyOtpByEmail(@RequestBody body: VerifyOtpByEmailRequest): ResponseEntity<*> {
        val user = authService.verifyOtpByEmail(body)
        return ResponseFactory.success(HttpStatus.OK, "OTP verified successfully", user)
    }

    // 🔹 Resend OTP (by userId)
    @PostMapping("/resend-otp")
    fun resendOtp(@RequestBody body: ResendOtpRequest): ResponseEntity<*> {
        authService.resendOtp(body)
        return ResponseFactory.success(HttpStatus.OK, "New OTP sent to your email", "")
    }

    // 🔹 Resend OTP by email
    @PostMapping("/resend-otp/email")
    fun resendOtpByEmail(@RequestBody body: ResendOtpByEmailRequest): ResponseEntity<*> {
        authService.resendOtpByEmail(body)
        return ResponseFactory.success(HttpStatus.OK, "New OTP sent to your email", "")
    }

    // 🔹 Forgot password (send OTP)
    @PostMapping("/forget-password/send-otp")
    fun forgetPasswordSendOtp(@RequestBody body: ForgetPasswordSendOtpRequest): ResponseEntity<*> {
        authService.forgetPasswordSendOtp(body)
        return ResponseFactory.success(HttpStatus.OK, "OTP sent to your email.", "")
    }

    // 🔹 Reset password
    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody body: ResetPasswordRequest): ResponseEntity<*> {
        authService.resetPassword(body)
        return ResponseFactory.success(HttpStatus.OK, "Password reset successfully", "")
    }

    // 🔹 Get all users (admin/debug)
    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<*> {
        val users = authService.getAllUsers()
        return ResponseFactory.success(HttpStatus.OK, "Fetched all users", users)
    }
}