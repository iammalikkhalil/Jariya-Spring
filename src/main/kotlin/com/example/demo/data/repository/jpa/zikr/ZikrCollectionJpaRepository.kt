package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrCollectionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrCollectionJpaRepository : JpaRepository<ZikrCollectionEntity, UUID> {

    // ✅ Eager load single query read (JOIN FETCH ready for future relations)
    @Query("""
        SELECT zc
        FROM ZikrCollectionEntity zc
        WHERE zc.isDeleted = false
        ORDER BY zc.orderIndex ASC
    """)
    fun findAllActive(): List<ZikrCollectionEntity>

    // ✅ Fetch updated records efficiently
    @Query("""
        SELECT zc
        FROM ZikrCollectionEntity zc
        WHERE zc.updatedAt > :updatedAt
          AND zc.isDeleted = false
        ORDER BY zc.updatedAt DESC
    """)
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrCollectionEntity>

    // ✅ Soft delete via native SQL (fast)
    @Transactional
    @Modifying
    @Query(
        value = "UPDATE zikr_collections SET is_deleted = true, deleted_at = :deletedAt WHERE id = :id",
        nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
