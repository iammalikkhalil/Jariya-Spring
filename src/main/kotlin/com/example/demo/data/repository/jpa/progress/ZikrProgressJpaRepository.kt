package com.example.demo.data.repository.jpa.progress

import com.example.demo.data.entity.ZikrProgressEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface ZikrProgressJpaRepository : JpaRepository<ZikrProgressEntity, UUID> {

    fun findAllByIsDeletedFalse(): List<ZikrProgressEntity>

    fun findAllByIsCompletedFalse(): List<ZikrProgressEntity>

    fun findByUpdatedAtAfter(updatedAt: Instant): List<ZikrProgressEntity>

    @Modifying
    @Query("UPDATE ZikrProgressEntity z SET z.isDeleted = true, z.deletedAt = :deletedAt WHERE z.id = :id")
    fun markAsDeleted(@Param("id") id: UUID, @Param("deletedAt") deletedAt: Instant): Int

    @Modifying
    @Query("UPDATE ZikrProgressEntity z SET z.processedLevels = :level, z.isStarted = true, z.updatedAt = :updatedAt WHERE z.id = :id")
    fun incrementProgress(
        @Param("id") id: UUID,
        @Param("level") level: Int,
        @Param("updatedAt") updatedAt: Instant
    ): Int

    @Modifying
    @Query("UPDATE ZikrProgressEntity z SET z.isCompleted = true, z.syncedAt = :now, z.updatedAt = :now WHERE z.id = :id")
    fun markAsComplete(@Param("id") id: UUID, @Param("now") now: Instant): Int
}
