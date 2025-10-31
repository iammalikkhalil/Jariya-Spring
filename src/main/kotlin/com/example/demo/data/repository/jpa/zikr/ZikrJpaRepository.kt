package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrJpaRepository : JpaRepository<ZikrEntity, UUID> {

    // ✅ Eager fetch for faster reads (no lazy load)
    @Query("""
        SELECT z
        FROM ZikrEntity z
        WHERE z.isDeleted = false
        ORDER BY z.updatedAt DESC
    """)
    fun findAllActive(): List<ZikrEntity>

    // ✅ Consistent eager fetch for incremental sync
    @Query("""
        SELECT z
        FROM ZikrEntity z
        WHERE z.updatedAt > :updatedAt
          AND z.isDeleted = false
        ORDER BY z.updatedAt DESC
    """)
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrEntity>

    // ✅ Native soft delete for minimum overhead
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = """
            UPDATE zikrs
            SET is_deleted = true, deleted_at = :deletedAt
            WHERE id = :id
        """, nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
