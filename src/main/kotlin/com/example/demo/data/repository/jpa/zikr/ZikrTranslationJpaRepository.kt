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

    fun findAllByIsDeletedFalse(): List<ZikrTranslationEntity>

    fun findByUpdatedAtAfter(updatedAt: Instant): List<ZikrTranslationEntity>

    @Transactional
    @Modifying
    @Query("UPDATE ZikrTranslationEntity z SET z.isDeleted = true, z.deletedAt = :deletedAt WHERE z.id = :id")
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
