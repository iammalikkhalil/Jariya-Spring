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

    // Read-only transaction avoids commit overhead
    @Transactional(readOnly = true)
    override fun getAllUsers(): List<UserModel> =
        userJpaRepository.findAll()
            .asSequence()                      // lazy map, less memory
            .map { it.toModel() }
            .toList()

    @Transactional(readOnly = true)
    override fun findUserByEmail(email: String): UserModel? =
        userJpaRepository.findByEmail(email)?.toModel()

    @Transactional(readOnly = true)
    override fun findUserById(id: String): UserModel? =
        userJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional(rollbackFor = [Exception::class])
    override fun registerUser(user: UserModel): Boolean {
        return try {
            if (userJpaRepository.findByEmail(user.email) != null) return false
            userJpaRepository.saveAndFlush(user.toEntity())     // immediate persistence
            Log.info("Registered new user: ${user.email}")
            true
        } catch (e: Exception) {
            Log.error("Error registering user: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateUser(user: UserModel): Boolean {
        return try {
            if (!userJpaRepository.existsById(user.id.toUUID())) return false
            userJpaRepository.saveAndFlush(user.toEntity())     // flush for consistency
            true
        } catch (e: Exception) {
            Log.error("Error updating user: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun resetPassword(userId: String, newPassword: String): Boolean {
        return try {
            userJpaRepository.resetPassword(
                userId.toUUID(), newPassword, Instant.now()
            ) > 0
        } catch (e: Exception) {
            Log.error("Error resetting password: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun verifyUser(email: String): Boolean {
        return try {
            userJpaRepository.verifyUser(email) > 0
        } catch (e: Exception) {
            Log.error("Error verifying user: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun markUserAsVerified(userId: String): Boolean {
        return try {
            val user = userJpaRepository.findById(userId.toUUID()).orElse(null)
                ?: return false
            if (user.isVerified) return true  // skip redundant updates
            val updated = user.copy(isVerified = true, updatedAt = Instant.now())
            userJpaRepository.saveAndFlush(updated)
            true
        } catch (e: Exception) {
            Log.error("Error marking user verified: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true)
    override fun getUserByReferralCode(referralCode: String): UserModel? =
        userJpaRepository.findByReferralCode(referralCode)?.toModel()

    @Transactional(readOnly = true)
    override fun isVerifiedUser(email: String): Boolean =
        userJpaRepository.findByEmail(email)?.isVerified ?: false

    @Transactional(readOnly = true)
    override fun isAlreadyReferred(userId: String): Boolean =
        userJpaRepository.findById(userId.toUUID()).orElse(null)?.referredBy == null

    @Transactional(readOnly = true)
    override fun getParentByChildId(id: String): UserModel? {
        val user = userJpaRepository.findById(id.toUUID()).orElse(null)
        return user?.referredBy?.let { ref ->
            userJpaRepository.findById(ref).orElse(null)?.toModel()
        }
    }

    @Transactional(readOnly = true)
    override fun isAlreadyParent(child: String, parent: String): Boolean {
        var parentId = parent
        while (true) {
            val parentUser = userJpaRepository.findById(parentId.toUUID()).orElse(null) ?: break
            if (parentUser.id.toString() == child) return true
            parentUser.referredBy ?: break
            parentId = parentUser.referredBy.toString()
        }
        return false
    }
}