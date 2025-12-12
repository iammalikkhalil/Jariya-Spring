package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrGoalMapEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrGoalMapJpaRepository : JpaRepository<ZikrGoalMapEntity, UUID> {

    @Query(
        """
        SELECT gm
        FROM ZikrGoalMapEntity gm
        JOIN FETCH gm.zikr z
        JOIN FETCH gm.goal g
        WHERE gm.isDeleted = false
        ORDER BY g.orderIndex ASC, gm.orderIndex ASC
    """
    )
    fun findAllActive(): List<ZikrGoalMapEntity>

    @Query(
        """
        SELECT gm
        FROM ZikrGoalMapEntity gm
        JOIN FETCH gm.zikr z
        JOIN FETCH gm.goal g
        WHERE gm.updatedAt > :updatedAt
          AND gm.isDeleted = false
        ORDER BY gm.updatedAt DESC
    """
    )
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrGoalMapEntity>

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = """
            UPDATE zikr_goal_map
            SET is_deleted = true, deleted_at = :deletedAt
            WHERE id = :id
        """,
        nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
