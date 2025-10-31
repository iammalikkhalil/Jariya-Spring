package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrHadithEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrHadithJpaRepository : JpaRepository<ZikrHadithEntity, UUID> {

    // ✅ Eager load zikr with JOIN FETCH to avoid N+1 queries
    @Query("""
        SELECT zh
        FROM ZikrHadithEntity zh
        JOIN FETCH zh.zikr z
        WHERE zh.isDeleted = false
        ORDER BY zh.updatedAt DESC
    """)
    fun findAllActive(): List<ZikrHadithEntity>

    // ✅ Consistent eager fetch for incremental sync
    @Query("""
        SELECT zh
        FROM ZikrHadithEntity zh
        JOIN FETCH zh.zikr z
        WHERE zh.updatedAt > :updatedAt
          AND zh.isDeleted = false
        ORDER BY zh.updatedAt DESC
    """)
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrHadithEntity>

    // ✅ Fast soft-delete native query (no Hibernate overhead)
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = "UPDATE zikr_hadiths SET is_deleted = true, deleted_at = :deletedAt WHERE id = :id",
        nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
