package com.example.demo.data.repository.impl.auth

import com.example.demo.data.mapper.auth.toEntity
import com.example.demo.data.mapper.auth.toModel
import com.example.demo.data.repository.jpa.auth.UserJpaRepository
import com.example.demo.domain.model.auth.UserModel
import com.example.demo.domain.repository.auth.UserRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun getAllUsers(): List<UserModel> =
        userJpaRepository.findAll().map { it.toModel() }

    override fun findUserByEmail(email: String): UserModel? =
        userJpaRepository.findByEmail(email)?.toModel()

    override fun findUserById(id: String): UserModel? =
        userJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun registerUser(user: UserModel): Boolean {
        return try {
            if (findUserByEmail(user.email) != null) return false
            userJpaRepository.save(user.toEntity())
            Log.info("Registered new user: ${user.email}")
            true
        } catch (e: Exception) {
            Log.error("Error registering user: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun updateUser(user: UserModel): Boolean {
        return try {
            if (!userJpaRepository.existsById(user.id.toUUID())) return false
            userJpaRepository.save(user.toEntity())
            true
        } catch (e: Exception) {
            Log.error("Error updating user: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun resetPassword(userId: String, newPassword: String): Boolean {
        return try {
            val updated = userJpaRepository.resetPassword(
                userId.toUUID(), newPassword, Instant.now()
            )
            updated > 0
        } catch (e: Exception) {
            Log.error("Error resetting password: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun verifyUser(email: String): Boolean {
        return try {
            userJpaRepository.verifyUser(email) > 0
        } catch (e: Exception) {
            Log.error("Error verifying user: ${e.message}", e)
            false
        }
    }

    override fun markUserAsVerified(userId: String): Boolean {
        return try {
            val user = userJpaRepository.findById(userId.toUUID()).orElse(null)
                ?: return false
            val updated = user.copy(isVerified = true)
            userJpaRepository.save(updated)
            true
        } catch (e: Exception) {
            Log.error("Error marking user verified: ${e.message}", e)
            false
        }
    }

    override fun getUserByReferralCode(referralCode: String): UserModel? =
        userJpaRepository.findByReferralCode(referralCode)?.toModel()

    // Simplified helpers
    override fun isVerifiedUser(email: String): Boolean =
        findUserByEmail(email)?.isVerified ?: false

    override fun isAlreadyReferred(userId: String): Boolean =
        findUserById(userId)?.referredBy == null

    override fun getParentByChildId(id: String): UserModel? {
        val user = findUserById(id)
        return if (user?.referredBy != null) findUserById(user.referredBy) else null
    }

    override fun isAlreadyParent(child: String, parent: String): Boolean {
        var parentId = parent
        while (true) {
            val parentUser = findUserById(parentId) ?: break
            if (parentUser.id == child) return true
            if (parentUser.referredBy == null) break
            parentId = parentUser.referredBy
        }
        return false
    }
}