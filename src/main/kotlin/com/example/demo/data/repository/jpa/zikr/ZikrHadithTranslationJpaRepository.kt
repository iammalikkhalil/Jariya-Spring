package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrHadithTranslationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrHadithTranslationJpaRepository : JpaRepository<ZikrHadithTranslationEntity, UUID> {

    // ✅ Eager load hadith + zikr to prevent N+1 fetch
    @Query("""
        SELECT zht
        FROM ZikrHadithTranslationEntity zht
        JOIN FETCH zht.hadith zh
        JOIN FETCH zh.zikr z
        WHERE zht.isDeleted = false
        ORDER BY zht.updatedAt DESC
    """)
    fun findAllActive(): List<ZikrHadithTranslationEntity>

    // ✅ Consistent eager fetch for incremental sync
    @Query("""
        SELECT zht
        FROM ZikrHadithTranslationEntity zht
        JOIN FETCH zht.hadith zh
        JOIN FETCH zh.zikr z
        WHERE zht.updatedAt > :updatedAt
          AND zht.isDeleted = false
        ORDER BY zht.updatedAt DESC
    """)
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrHadithTranslationEntity>

    // ✅ Lightweight native soft delete
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = """
            UPDATE zikr_hadith_translations
            SET is_deleted = true, deleted_at = :deletedAt
            WHERE id = :id
        """,
        nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
