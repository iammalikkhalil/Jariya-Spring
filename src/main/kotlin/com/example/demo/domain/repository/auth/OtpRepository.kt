package com.example.demo.domain.repository.auth

import com.example.demo.domain.model.auth.OtpModel


interface OtpRepository {
    fun generateOtp(otp: OtpModel): Boolean
    fun findOtpById(id: String): OtpModel?
    fun incrementOtpAttempts(id: String): Boolean
    fun deleteOtpById(id: String): Boolean
}
