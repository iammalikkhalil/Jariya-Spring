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

    // ✅ NEW: Count all non-deleted users
//    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.isDeleted = false AND u.isActive = true AND u.isVerified = true")
//    fun countAllActiveVerifiedUsers(): Long
//
//


// because guests are not in userTable so, thats why we are using points table

    @Query(
        value = """
        SELECT COUNT(*) FROM (
            SELECT user_id FROM zikr_points 
            WHERE is_deleted = false 
            AND user_id ~ '^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}${'$'}'
            UNION
            SELECT source_user FROM zikr_points 
            WHERE source_user IS NOT NULL 
            AND is_deleted = false
            AND source_user ~ '^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}${'$'}'
        ) AS all_users
    """,
        nativeQuery = true
    )
    fun countAllActiveVerifiedUsers(): Long

}
