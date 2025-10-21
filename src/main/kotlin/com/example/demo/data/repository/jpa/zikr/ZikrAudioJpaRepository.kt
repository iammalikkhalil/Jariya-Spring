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

    fun findAllByIsDeletedFalse(): List<ZikrAudioEntity>

    fun findByUpdatedAtAfter(updatedAt: Instant): List<ZikrAudioEntity>

    @Transactional
    @Modifying
    @Query("UPDATE ZikrAudioEntity z SET z.isDeleted = true, z.deletedAt = :deletedAt WHERE z.id = :id")
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}