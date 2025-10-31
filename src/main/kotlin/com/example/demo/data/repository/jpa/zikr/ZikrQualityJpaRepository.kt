package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrQualityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrQualityJpaRepository : JpaRepository<ZikrQualityEntity, UUID> {

    // ✅ Eagerly load zikr to avoid N+1 issues
    @Query("""
        SELECT zq
        FROM ZikrQualityEntity zq
        JOIN FETCH zq.zikr z
        WHERE zq.isDeleted = false
        ORDER BY zq.updatedAt DESC
    """)
    fun findAllActive(): List<ZikrQualityEntity>

    // ✅ Consistent eager fetch for incremental sync
    @Query("""
        SELECT zq
        FROM ZikrQualityEntity zq
        JOIN FETCH zq.zikr z
        WHERE zq.updatedAt > :updatedAt
          AND zq.isDeleted = false
        ORDER BY zq.updatedAt DESC
    """)
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrQualityEntity>

    // ✅ Lightweight native soft delete
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = """
            UPDATE zikr_qualities
            SET is_deleted = true, deleted_at = :deletedAt
            WHERE id = :id
        """, nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
