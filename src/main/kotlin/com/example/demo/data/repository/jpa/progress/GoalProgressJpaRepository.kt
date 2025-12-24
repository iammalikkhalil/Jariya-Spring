package com.example.demo.data.repository.jpa.progress

import com.example.demo.data.entity.GoalProgressEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface GoalProgressJpaRepository : JpaRepository<GoalProgressEntity, UUID> {

    // ✅ JOIN FETCH ensures user & zikr are loaded in single query (no N+1)
    @Query("""
        SELECT zp
        FROM GoalProgressEntity zp
        JOIN FETCH zp.user u
        LEFT JOIN FETCH zp.zikr z
        WHERE zp.isDeleted = false
        ORDER BY zp.updatedAt DESC
    """)
    fun findAllActive(): List<GoalProgressEntity>

    // ✅ Fetch uncompleted with eager relations
    @Query("""
        SELECT zp
        FROM GoalProgressEntity zp
        JOIN FETCH zp.user u
        LEFT JOIN FETCH zp.zikr z
        WHERE zp.isCompleted = false
          AND zp.isDeleted = false
        ORDER BY zp.updatedAt DESC
    """)
    fun findUncompleted(): List<GoalProgressEntity>

    // ✅ Fetch updated entries with eager joins
    @Query("""
        SELECT zp
        FROM GoalProgressEntity zp
        JOIN FETCH zp.user u
        LEFT JOIN FETCH zp.zikr z
        WHERE zp.updatedAt > :updatedAt
          AND zp.isDeleted = false
        ORDER BY zp.updatedAt DESC
    """)
    fun findUpdatedAfter(@Param("updatedAt") updatedAt: Instant): List<GoalProgressEntity>

    // ✅ Soft delete (native for speed)
    @Modifying
    @Query(
        value = "UPDATE zikr_progress SET is_deleted = true, deleted_at = :deletedAt WHERE id = :id",
        nativeQuery = true
    )
    fun markAsDeleted(@Param("id") id: UUID, @Param("deletedAt") deletedAt: Instant): Int

    // ✅ Increment progress via native update
    @Modifying
    @Query(
        value = """
        UPDATE zikr_progress
        SET processed_levels = :level,
            is_started = true,
            updated_at = :updatedAt
        WHERE id = :id
        """,
        nativeQuery = true
    )
    fun incrementProgress(
        @Param("id") id: UUID,
        @Param("level") level: Int,
        @Param("updatedAt") updatedAt: Instant
    ): Int

    // ✅ Mark as complete via native update
    @Modifying
    @Query(
        value = """
        UPDATE zikr_progress
        SET is_completed = true,
            synced_at = :now,
            updated_at = :now
        WHERE id = :id
        """,
        nativeQuery = true
    )
    fun markAsComplete(@Param("id") id: UUID, @Param("now") now: Instant): Int
}
