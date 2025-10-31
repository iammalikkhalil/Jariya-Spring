package com.example.demo.data.repository.impl.auth

import com.example.demo.data.mapper.auth.toEntity
import com.example.demo.data.mapper.auth.toModel
import com.example.demo.data.repository.jpa.auth.OtpJpaRepository
import com.example.demo.domain.model.auth.OtpModel
import com.example.demo.domain.repository.auth.OtpRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class OtpRepositoryImpl(
    private val otpJpaRepository: OtpJpaRepository
) : OtpRepository {

    @Transactional(rollbackFor = [Exception::class])
    override fun generateOtp(otp: OtpModel): Boolean {
        return try {
            otpJpaRepository.saveAndFlush(otp.toEntity())   // flush immediately for better consistency
            true
        } catch (e: Exception) {
            Log.error("Error generating OTP: ${e.message}", e)
            false
        }
    }

    // Mark read-only for optimization, avoids transaction overhead
    @Transactional(readOnly = true)
    override fun findOtpById(id: String): OtpModel? {
        return try {
            otpJpaRepository.findOtpById(id.toUUID())?.toModel()
        } catch (e: Exception) {
            Log.error("Error finding OTP: ${e.message}", e)
            null
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun incrementOtpAttempts(id: String): Boolean {
        return try {
            otpJpaRepository.incrementOtpAttempts(id.toUUID()) > 0
        } catch (e: Exception) {
            Log.error("Error incrementing OTP attempts: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteOtpById(id: String): Boolean {
        return try {
            otpJpaRepository.deleteOtpById(id.toUUID()) > 0
        } catch (e: Exception) {
            Log.error("Error deleting OTP: ${e.message}", e)
            false
        }
    }
}
