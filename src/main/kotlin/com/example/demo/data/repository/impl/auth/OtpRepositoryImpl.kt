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

    @Transactional
    override fun generateOtp(otp: OtpModel): Boolean {
        return try {
            otpJpaRepository.save(otp.toEntity())
            true
        } catch (e: Exception) {
            Log.error("Error generating OTP: ${e.message}", e)
            false
        }
    }

    override fun findOtpById(id: String): OtpModel? {
        return try {
            val entity = otpJpaRepository.findOtpById(id.toUUID())
            entity?.toModel()
        } catch (e: Exception) {
            Log.error("Error finding OTP: ${e.message}", e)
            null
        }
    }

    @Transactional
    override fun incrementOtpAttempts(id: String): Boolean {
        return try {
            otpJpaRepository.incrementOtpAttempts(id.toUUID()) > 0
        } catch (e: Exception) {
            Log.error("Error incrementing OTP attempts: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun deleteOtpById(id: String): Boolean {
        return try {
            otpJpaRepository.deleteOtpById(id.toUUID()) > 0
        } catch (e: Exception) {
            Log.error("Error deleting OTP: ${e.message}", e)
            false
        }
    }
}