package com.example.demo.data.repository.jpa.progress

import com.example.demo.data.entity.ZikrPointEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface ZikrPointJpaRepository : JpaRepository<ZikrPointEntity, UUID> {

    fun findAllByIsDeletedFalse(): List<ZikrPointEntity>

    fun findByUpdatedAtAfter(updatedAt: Instant): List<ZikrPointEntity>

    @Query("SELECT CASE WHEN COUNT(z) > 0 THEN true ELSE false END FROM ZikrPointEntity z WHERE z.progress.id = :progressId AND z.user.id = :userId AND z.level = :level AND z.isDeleted = false")
    fun existsByProgressAndUserAndLevel(
        @Param("progressId") progressId: UUID,
        @Param("userId") userId: UUID,
        @Param("level") level: Int
    ): Boolean

    @Query("SELECT SUM(z.points) FROM ZikrPointEntity z WHERE z.user.id = :userId AND z.sourceType = 'REFERRAL' AND z.isDeleted = false")
    fun getReferralPoints(@Param("userId") userId: UUID): Int?

    @Query("SELECT SUM(z.points) FROM ZikrPointEntity z WHERE z.user.id = :userId AND z.sourceType = 'ZIKR' AND z.isDeleted = false")
    fun getZikrPoints(@Param("userId") userId: UUID): Int?

    @Query("SELECT SUM(z.points) FROM ZikrPointEntity z WHERE z.sourceType = 'ZIKR' AND z.isDeleted = false")
    fun getTotalZikrPoints(): Int?

    @Modifying
    @Query("UPDATE ZikrPointEntity z SET z.isDeleted = true, z.deletedAt = :deletedAt WHERE z.id = :id")
    fun markAsDeleted(@Param("id") id: UUID, @Param("deletedAt") deletedAt: Instant): Int
}
