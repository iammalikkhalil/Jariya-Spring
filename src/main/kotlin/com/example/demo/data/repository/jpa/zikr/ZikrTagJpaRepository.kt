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

    fun findAllByIsDeletedFalse(): List<ZikrTagEntity>

    fun findByUpdatedAtAfter(updatedAt: Instant): List<ZikrTagEntity>

    @Transactional
    @Modifying
    @Query("UPDATE ZikrTagEntity z SET z.isDeleted = true, z.deletedAt = :deletedAt WHERE z.id = :id")
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
