package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrTagMapEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrTagMapJpaRepository : JpaRepository<ZikrTagMapEntity, UUID> {

    // ✅ Eager load zikr and tag in one go (no N+1)
    @Query("""
        SELECT ztm
        FROM ZikrTagMapEntity ztm
        JOIN FETCH ztm.zikr z
        JOIN FETCH ztm.tag t
        WHERE ztm.isDeleted = false
        ORDER BY ztm.updatedAt DESC
    """)
    fun findAllActive(): List<ZikrTagMapEntity>

    // ✅ Same for incremental updates
    @Query("""
        SELECT ztm
        FROM ZikrTagMapEntity ztm
        JOIN FETCH ztm.zikr z
        JOIN FETCH ztm.tag t
        WHERE ztm.updatedAt > :updatedAt
          AND ztm.isDeleted = false
        ORDER BY ztm.updatedAt DESC
    """)
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrTagMapEntity>

    // ✅ Soft delete optimized with native query
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = """
            UPDATE zikr_tag_map
            SET is_deleted = true, deleted_at = :deletedAt
            WHERE id = :id
        """,
        nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
