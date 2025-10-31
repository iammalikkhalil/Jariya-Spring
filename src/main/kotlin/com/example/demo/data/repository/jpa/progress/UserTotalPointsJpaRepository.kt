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

    // ✅ Native query: Faster bulk reads
    @Query(
        value = "SELECT * FROM user_total_points WHERE is_deleted = false",
        nativeQuery = true
    )
    fun findAllActive(): List<UserTotalPointEntity>

    // ✅ Native query: For incremental sync / updated rows
    @Query(
        value = "SELECT * FROM user_total_points WHERE updated_at > :updatedAt",
        nativeQuery = true
    )
    fun findUpdatedAfter(@Param("updatedAt") updatedAt: Instant): List<UserTotalPointEntity>

    // ✅ Native query: Soft delete (no ORM flush)
    @Modifying(clearAutomatically = true, flushAutomatically = false)
    @Query(
        value = "UPDATE user_total_points SET is_deleted = true, deleted_at = :deletedAt WHERE id = :id",
        nativeQuery = true
    )
    fun markAsDeleted(@Param("id") id: UUID, @Param("deletedAt") deletedAt: Instant): Int
}