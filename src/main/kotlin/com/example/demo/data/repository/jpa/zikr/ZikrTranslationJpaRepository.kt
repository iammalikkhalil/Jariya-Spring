package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrTranslationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrTranslationJpaRepository : JpaRepository<ZikrTranslationEntity, UUID> {

    // ✅ Eager fetch to load zikr in one go
    @Query("""
        SELECT zt
        FROM ZikrTranslationEntity zt
        JOIN FETCH zt.zikr z
        WHERE zt.isDeleted = false
        ORDER BY zt.updatedAt DESC
    """)
    fun findAllActive(): List<ZikrTranslationEntity>

    // ✅ Same pattern for incremental sync
    @Query("""
        SELECT zt
        FROM ZikrTranslationEntity zt
        JOIN FETCH zt.zikr z
        WHERE zt.updatedAt > :updatedAt
          AND zt.isDeleted = false
        ORDER BY zt.updatedAt DESC
    """)
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrTranslationEntity>

    // ✅ Lightweight native soft delete
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = """
            UPDATE zikr_translations
            SET is_deleted = true, deleted_at = :deletedAt
            WHERE id = :id
        """,
        nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
