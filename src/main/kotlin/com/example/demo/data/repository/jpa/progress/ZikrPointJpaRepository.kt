package com.example.demo.data.repository.jpa.progress

import com.example.demo.data.entity.ZikrPointEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface ZikrPointJpaRepository : JpaRepository<ZikrPointEntity, UUID> {

    // ✅ JOIN FETCH to load related entities (no N+1, 1 SQL query)
    @Query("""
        SELECT zp
        FROM ZikrPointEntity zp
        JOIN FETCH zp.user u
        LEFT JOIN FETCH zp.zikr z
        JOIN FETCH zp.sourceUser su
        WHERE zp.isDeleted = false
        ORDER BY zp.updatedAt DESC
    """)
    fun findAllActive(): List<ZikrPointEntity>

    @Query("""
        SELECT zp
        FROM ZikrPointEntity zp
        JOIN FETCH zp.user u
        LEFT JOIN FETCH zp.zikr z
        JOIN FETCH zp.sourceUser su
        WHERE zp.updatedAt > :updatedAt
          AND zp.isDeleted = false
        ORDER BY zp.updatedAt DESC
    """)
    fun findUpdatedAfter(@Param("updatedAt") updatedAt: Instant): List<ZikrPointEntity>

    // ✅ Boolean existence check (native for speed)
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
        @Param("progressId") progressId: UUID,
        @Param("userId") userId: UUID,
        @Param("level") level: Int
    ): Boolean

    // ✅ Aggregates remain native (faster)
    @Query(
        value = """
        SELECT COALESCE(SUM(points), 0)
        FROM zikr_points
        WHERE user_id = :userId AND source_type = 'REFERRAL' AND is_deleted = false
        """,
        nativeQuery = true
    )
    fun getReferralPoints(@Param("userId") userId: UUID): Int?

    @Query(
        value = """
        SELECT COALESCE(SUM(points), 0)
        FROM zikr_points
        WHERE user_id = :userId AND source_type = 'ZIKR' AND is_deleted = false
        """,
        nativeQuery = true
    )
    fun getZikrPoints(@Param("userId") userId: UUID): Int?

    @Query(
        value = """
        SELECT COALESCE(SUM(points), 0)
        FROM zikr_points
        WHERE source_type = 'ZIKR' AND is_deleted = false
        """,
        nativeQuery = true
    )
    fun getTotalZikrPoints(): Int?

    // ✅ Native for soft delete
    @Modifying
    @Query(
        value = "UPDATE zikr_points SET is_deleted = true, deleted_at = :deletedAt WHERE id = :id",
        nativeQuery = true
    )
    fun markAsDeleted(@Param("id") id: UUID, @Param("deletedAt") deletedAt: Instant): Int
}
