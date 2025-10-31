package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrRewardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrRewardJpaRepository : JpaRepository<ZikrRewardEntity, UUID> {

    // ✅ Eager fetch to avoid N+1 (load zikr in single query)
    @Query("""
        SELECT zr
        FROM ZikrRewardEntity zr
        JOIN FETCH zr.zikr z
        WHERE zr.isDeleted = false
        ORDER BY zr.updatedAt DESC
    """)
    fun findAllActive(): List<ZikrRewardEntity>

    // ✅ Incremental sync with eager load
    @Query("""
        SELECT zr
        FROM ZikrRewardEntity zr
        JOIN FETCH zr.zikr z
        WHERE zr.updatedAt > :updatedAt
          AND zr.isDeleted = false
        ORDER BY zr.updatedAt DESC
    """)
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrRewardEntity>

    // ✅ Lightweight native soft delete
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = """
            UPDATE zikr_rewards
            SET is_deleted = true, deleted_at = :deletedAt
            WHERE id = :id
        """, nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
