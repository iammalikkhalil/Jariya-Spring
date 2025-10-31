package com.example.demo.data.repository.jpa.auth

import com.example.demo.data.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, UUID> {

    fun findByEmail(email: String): UserEntity?

    @Query("SELECT u FROM UserEntity u WHERE u.referralCode = :referralCode")
    fun findByReferralCode(referralCode: String): UserEntity?

    @Modifying(clearAutomatically = true, flushAutomatically = false)
    @Query("UPDATE UserEntity u SET u.isVerified = true WHERE u.email = :email")
    fun verifyUser(email: String): Int

    @Modifying(clearAutomatically = true, flushAutomatically = false)
    @Query("UPDATE UserEntity u SET u.password = :password, u.updatedAt = :updatedAt WHERE u.id = :id")
    fun resetPassword(id: UUID, password: String, updatedAt: Instant): Int
}
