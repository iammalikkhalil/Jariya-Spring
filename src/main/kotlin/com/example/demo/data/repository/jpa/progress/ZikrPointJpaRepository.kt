package com.example.demo.data.repository.jpa.progress

import com.example.demo.data.entity.ZikrPointEntity
import com.example.demo.domain.model.zikr.GoalStatsProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

@Repository
interface ZikrPointJpaRepository : JpaRepository<ZikrPointEntity, UUID> {

    @Query(
        """
        SELECT zp
        FROM ZikrPointEntity zp
        LEFT JOIN FETCH zp.zikr z
        WHERE zp.isDeleted = false
        ORDER BY zp.updatedAt DESC
        """
    )
    fun findAllActive(): List<ZikrPointEntity>

    @Query(
        """
        SELECT zp
        FROM ZikrPointEntity zp
        LEFT JOIN FETCH zp.zikr z
        WHERE zp.updatedAt > :updatedAt
          AND zp.isDeleted = false
        ORDER BY zp.updatedAt DESC
        """
    )
    fun findUpdatedAfter(@Param("updatedAt") updatedAt: Instant): List<ZikrPointEntity>

    // ✅ FIX: userId is String now
    @Query(
        value = """
        SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
        FROM zikr_points 
        WHERE progress_id = :progressId 
          AND user_id = :userId 
          AND level = :level
          AND is_deleted = false
        """,
        nativeQuery = true
    )
    fun existsByProgressAndUserAndLevel(
        @Param("progressId") progressId: String,
        @Param("userId") userId: String,
        @Param("level") level: Int
    ): Boolean

    // ✅ FIX: userId is String now
    @Query(
        value = """
        SELECT COALESCE(SUM(points), 0)
        FROM zikr_points
        WHERE user_id = :userId AND source_type = 'REFERRAL' AND is_deleted = false
        """,
        nativeQuery = true
    )
    fun getReferralPoints(@Param("userId") userId: String): Int?

    // ✅ FIX: userId is String now
    @Query(
        value = """
        SELECT COALESCE(SUM(points), 0)
        FROM zikr_points
        WHERE user_id = :userId AND source_type = 'ZIKR' AND is_deleted = false
        """,
        nativeQuery = true
    )
    fun getZikrPoints(@Param("userId") userId: String): Int?

    @Query(
        value = """
        SELECT COALESCE(SUM(points), 0)
        FROM zikr_points
        WHERE source_type = 'ZIKR' AND is_deleted = false
        """,
        nativeQuery = true
    )
    fun getTotalZikrPoints(): Int?

    @Modifying
    @Query(
        value = "UPDATE zikr_points SET is_deleted = true, deleted_at = :deletedAt WHERE id = :id",
        nativeQuery = true
    )
    fun markAsDeleted(@Param("id") id: UUID, @Param("deletedAt") deletedAt: Instant): Int

    @Query(
        value = """
        SELECT
            gp.goal_id AS goalId,
            COALESCE(SUM(zp.points), 0) AS earnedHasanats
        FROM zikr_points zp
        INNER JOIN goal_progress gp
            ON gp.id = zp.progress_id::uuid
            AND zp.progress_id ~ '^[0-9a-fA-F-]{36}${'$'}'
        WHERE zp.progress_type = 'goal'
          AND zp.is_deleted = false
          AND gp.is_deleted = false
          AND gp.goal_id IS NOT NULL
        GROUP BY gp.goal_id
        """,
        nativeQuery = true
    )
    fun getGoalsStatsGrouped(): List<GoalStatsProjection>

    @Query(
        value = """
        SELECT COUNT(DISTINCT user_id)
        FROM zikr_points
        WHERE is_deleted = false
          AND user_id IS NOT NULL
        """,
        nativeQuery = true
    )
    fun countDistinctActiveUsers(): Long



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
    fun countDistinctUsers(): Long



}
