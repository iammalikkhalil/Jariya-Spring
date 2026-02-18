package com.example.demo.data.repository.jpa.progress

import com.example.demo.data.entity.GoalProgressEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

@Repository
interface GoalProgressJpaRepository : JpaRepository<GoalProgressEntity, UUID> {

    @Query(
        """
        SELECT gp
        FROM GoalProgressEntity gp
        LEFT JOIN FETCH gp.zikr z
        LEFT JOIN FETCH gp.goal g
        WHERE gp.isDeleted = false
        ORDER BY gp.updatedAt DESC
        """
    )
    fun findAllActive(): List<GoalProgressEntity>


    @Query(
        """
        SELECT gp
        FROM GoalProgressEntity gp
        LEFT JOIN FETCH gp.zikr z
        LEFT JOIN FETCH gp.goal g
        WHERE gp.isCompleted = false
          AND gp.isDeleted = false
        ORDER BY gp.updatedAt DESC
        """
    )
    fun findUncompleted(): List<GoalProgressEntity>

    @Query(
        """
        SELECT gp
        FROM GoalProgressEntity gp
        LEFT JOIN FETCH gp.zikr z
        LEFT JOIN FETCH gp.goal g
        WHERE gp.updatedAt > :updatedAt
          AND gp.isDeleted = false
        ORDER BY gp.updatedAt DESC
        """
    )
    fun findUpdatedAfter(@Param("updatedAt") updatedAt: Instant): List<GoalProgressEntity>


    @Modifying
    @Query(
        value = """
    UPDATE goal_progress
    SET is_deleted = true,
        deleted_at = :deletedAt,
        updated_at = :deletedAt
    WHERE id = :id
    """,
        nativeQuery = true
    )
    fun markAsDeleted(
        @Param("id") id: UUID,
        @Param("deletedAt") deletedAt: Instant
    ): Int




    // ✅ FIX: Wrong table name in your query (you wrote zikr_progress)
    @Modifying
    @Query(
        value = """
        UPDATE goal_progress
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

    // ✅ FIX: Wrong table name in your query (you wrote zikr_progress)
    @Modifying
    @Query(
        value = """
        UPDATE goal_progress
        SET is_completed = true,
            synced_at = :now,
            updated_at = :now
        WHERE id = :id
        """,
        nativeQuery = true
    )
    fun markAsComplete(@Param("id") id: UUID, @Param("now") now: Instant): Int

    @Query(
        """
        SELECT gp
        FROM GoalProgressEntity gp
        WHERE gp.id IN :ids
        """
    )
    fun findAllByIds(@Param("ids") ids: List<UUID>): List<GoalProgressEntity>

    @Query(
        value = """
        SELECT COUNT(DISTINCT user_id)
        FROM goal_progress
        WHERE is_deleted = false
          AND user_id IS NOT NULL
        """,
        nativeQuery = true
    )
    fun countDistinctActiveUsers(): Long

    @Query(
        value = """
        SELECT *
        FROM goal_progress
        WHERE is_deleted = false
          AND goal_id IS NOT NULL
        """,
        nativeQuery = true
    )
    fun findAllActiveGoalProgress(): List<GoalProgressEntity>
}