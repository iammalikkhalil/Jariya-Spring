package com.example.demo.data.repository.jpa.progress

import com.example.demo.data.entity.UserDailySummaryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface UserDailySummaryJpaRepository : JpaRepository<UserDailySummaryEntity, UUID> {

    fun findAllByIsDeletedFalse(): List<UserDailySummaryEntity>

    fun findByUserIdAndIsDeletedFalse(userId: UUID): UserDailySummaryEntity?

    fun findByUpdatedAtAfter(updatedAt: Instant): List<UserDailySummaryEntity>

    @Modifying
    @Query("UPDATE UserDailySummaryEntity u SET u.isDeleted = true, u.deletedAt = :deletedAt WHERE u.id = :id")
    fun markAsDeleted(@Param("id") id: UUID, @Param("deletedAt") deletedAt: Instant): Int
}
