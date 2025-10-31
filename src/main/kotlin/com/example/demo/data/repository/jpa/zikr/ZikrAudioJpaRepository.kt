package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrAudioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrAudioJpaRepository : JpaRepository<ZikrAudioEntity, UUID> {

    // ✅ Eager load zikr in one query — prevents N+1 problem
    @Query("""
        SELECT za
        FROM ZikrAudioEntity za
        JOIN FETCH za.zikr z
        WHERE za.isDeleted = false
        ORDER BY za.updatedAt DESC
    """)
    fun findAllActive(): List<ZikrAudioEntity>

    // ✅ Fetch updated entries with eager zikr
    @Query("""
        SELECT za
        FROM ZikrAudioEntity za
        JOIN FETCH za.zikr z
        WHERE za.updatedAt > :updatedAt
          AND za.isDeleted = false
        ORDER BY za.updatedAt DESC
    """)
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrAudioEntity>

    // ✅ Soft delete for speed
    @Transactional
    @Modifying
    @Query(
        value = "UPDATE zikr_audio_files SET is_deleted = true, deleted_at = :deletedAt WHERE id = :id",
        nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
