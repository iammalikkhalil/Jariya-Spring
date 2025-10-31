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

    // ✅ Use JOIN FETCH to load user in one query (avoid N+1)
    @Query("""
        SELECT uds
        FROM UserDailySummaryEntity uds
        JOIN FETCH uds.user u
        WHERE uds.isDeleted = false
        ORDER BY uds.updatedAt DESC
    """)
    fun findAllActive(): List<UserDailySummaryEntity>

    // ✅ Load specific user’s summary eagerly
    @Query("""
        SELECT uds
        FROM UserDailySummaryEntity uds
        JOIN FETCH uds.user u
        WHERE u.id = :userId
          AND uds.isDeleted = false
    """)
    fun findActiveByUserId(@Param("userId") userId: UUID): UserDailySummaryEntity?

    // ✅ Filter updated ones efficiently
    @Query("""
        SELECT uds
        FROM UserDailySummaryEntity uds
        JOIN FETCH uds.user u
        WHERE uds.updatedAt > :updatedAt
          AND uds.isDeleted = false
        ORDER BY uds.updatedAt DESC
    """)
    fun findUpdatedAfter(@Param("updatedAt") updatedAt: Instant): List<UserDailySummaryEntity>

    @Modifying
    @Query("""
        UPDATE UserDailySummaryEntity uds
        SET uds.isDeleted = true, uds.deletedAt = :deletedAt
        WHERE uds.id = :id
    """)
    fun markAsDeleted(@Param("id") id: UUID, @Param("deletedAt") deletedAt: Instant): Int
}
