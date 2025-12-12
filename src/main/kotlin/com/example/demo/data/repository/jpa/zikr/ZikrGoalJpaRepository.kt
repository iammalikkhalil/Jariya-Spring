package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrGoalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrGoalJpaRepository : JpaRepository<ZikrGoalEntity, UUID> {

    @Query(
        """
        SELECT g
        FROM ZikrGoalEntity g
        WHERE g.isDeleted = false
        ORDER BY g.orderIndex ASC, g.createdAt DESC
    """
    )
    fun findAllActive(): List<ZikrGoalEntity>

    @Query(
        """
        SELECT g
        FROM ZikrGoalEntity g
        WHERE g.updatedAt > :updatedAt
          AND g.isDeleted = false
        ORDER BY g.updatedAt DESC
    """
    )
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrGoalEntity>

    @Transactional
    @Modifying
    @Query(
        value = """
            UPDATE zikr_goal
            SET is_deleted = true, deleted_at = :deletedAt
            WHERE id = :id
        """,
        nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
