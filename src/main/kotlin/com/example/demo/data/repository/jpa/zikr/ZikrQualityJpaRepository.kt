package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrQualityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrQualityJpaRepository : JpaRepository<ZikrQualityEntity, UUID> {

    fun findAllByIsDeletedFalse(): List<ZikrQualityEntity>

    fun findByUpdatedAtAfter(updatedAt: Instant): List<ZikrQualityEntity>

    @Transactional
    @Modifying
    @Query("UPDATE ZikrQualityEntity z SET z.isDeleted = true, z.deletedAt = :deletedAt WHERE z.id = :id")
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
