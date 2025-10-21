package com.example.demo.data.mapper.auth


import com.example.demo.data.entity.OtpEntity
import com.example.demo.domain.model.auth.OtpModel
import com.example.demo.presentation.dto.auth.OtpDto
import java.util.*

fun OtpEntity.toModel(): OtpModel = OtpModel(
    id = this.id.toString(),
    hashedOtp = this.hashedOtp,
    otp = this.hashedOtp,
    otpRequestedAt = this.otpRequestedAt,
    expiresAt = this.expiresAt,
    otpAttempts = this.otpAttempts
)

fun OtpModel.toEntity(): OtpEntity = OtpEntity(
    id = UUID.fromString(this.id),
    hashedOtp = this.hashedOtp,
    otpAttempts = this.otpAttempts,
    otpRequestedAt = this.otpRequestedAt,
    expiresAt = this.expiresAt
)


fun OtpDto.toModel(): OtpModel = OtpModel(
    id = this.id,
    hashedOtp = this.hashedOtp,
    otpAttempts = this.otpAttempts,
    otpRequestedAt = this.otpRequestedAt,
    expiresAt = this.expiresAt,
    otp = this.otp
)



fun OtpModel.toDto(): OtpDto = OtpDto(
    id = this.id,
    hashedOtp = this.hashedOtp,
    otpAttempts = this.otpAttempts,
    otpRequestedAt = this.otpRequestedAt,
    expiresAt = this.expiresAt,
    otp = this.otp
)
