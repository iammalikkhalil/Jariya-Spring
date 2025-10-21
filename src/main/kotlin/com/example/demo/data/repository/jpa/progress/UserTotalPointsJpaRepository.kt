package com.example.demo.data.repository.jpa.progress

import com.example.demo.data.entity.UserTotalPointEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface UserTotalPointsJpaRepository : JpaRepository<UserTotalPointEntity, UUID> {

    fun findAllByIsDeletedFalse(): List<UserTotalPointEntity>

    fun findByUpdatedAtAfter(updatedAt: Instant): List<UserTotalPointEntity>

    @Modifying
    @Query("UPDATE UserTotalPointEntity u SET u.isDeleted = true, u.deletedAt = :deletedAt WHERE u.id = :id")
    fun markAsDeleted(@Param("id") id: UUID, @Param("deletedAt") deletedAt: Instant): Int
}
