package com.example.demo.presentation.service.referral

import com.example.demo.domain.exception.AppException
import com.example.demo.domain.model.referral.ReferralResult
import com.example.demo.domain.model.referral.toDto
import com.example.demo.domain.repository.auth.UserRepository
import com.example.demo.domain.repository.referral.ReferralRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.presentation.dto.referral.ReferralDto
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ReferralService(
    private val referralRepository: ReferralRepository,
    private val userRepository: UserRepository
) {

    fun addReferral(userId: String, referralCode: String): ReferralResult {
        val start = System.currentTimeMillis()
        Log.info("🧩 [ReferralService] addReferral started: userId=$userId, referralCode=$referralCode")

            val child = userRepository.findUserById(userId)
                ?: throw AppException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found")

            val parent = userRepository.getUserByReferralCode(referralCode)
                ?: throw AppException(HttpStatus.BAD_REQUEST, "Invalid referral code")

            if (parent.id == child.id)
                throw AppException(HttpStatus.BAD_REQUEST, "Self-referral is not allowed")

            referralRepository.addReferral(parent.id, child.id)

            Log.info("✅ Referral established successfully: ${parent.email} → ${child.email} (${System.currentTimeMillis() - start}ms)")
            return ReferralResult.Success()
    }

    fun getReferralTreeUp(userId: String): List<ReferralDto> {
        val start = System.currentTimeMillis()
        val result = referralRepository.getReferralTreeUp(userId).map { it.toDto() }
        Log.debug("⬆️ getReferralTreeUp took ${System.currentTimeMillis() - start}ms (${result.size} items)")
        return result
    }

    fun getReferralTreeDown(userId: String): List<ReferralDto> {
        val start = System.currentTimeMillis()
        val result = referralRepository.getReferralTreeDown(userId).map { it.toDto() }
        Log.debug("⬇️ getReferralTreeDown took ${System.currentTimeMillis() - start}ms (${result.size} items)")
        return result
    }
}
