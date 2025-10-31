package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrTagEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrTagJpaRepository : JpaRepository<ZikrTagEntity, UUID> {

    // ✅ Fetch only active (fast lightweight query)
    @Query(
        value = """
            SELECT * FROM zikr_tags
            WHERE is_deleted = false
            ORDER BY updated_at DESC
        """,
        nativeQuery = true
    )
    fun findAllActive(): List<ZikrTagEntity>

    // ✅ Incremental sync query — native, fast
    @Query(
        value = """
            SELECT * FROM zikr_tags
            WHERE updated_at > :updatedAt
              AND is_deleted = false
            ORDER BY updated_at DESC
        """,
        nativeQuery = true
    )
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrTagEntity>

    // ✅ Lightweight native soft delete
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = """
            UPDATE zikr_tags
            SET is_deleted = true, deleted_at = :deletedAt
            WHERE id = :id
        """,
        nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
