package com.example.demo.data.repository.jpa.progress

import com.example.demo.data.entity.QuranProgressEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

@Repository
interface QuranProgressJpaRepository : JpaRepository<QuranProgressEntity, UUID> {

    @Query(
        """
        SELECT qp
        FROM QuranProgressEntity qp
        WHERE qp.isDeleted = false
        ORDER BY qp.updatedAt DESC
        """
    )
    fun findAllActive(): List<QuranProgressEntity>


    @Query(
        """
        SELECT qp
        FROM QuranProgressEntity qp
        WHERE qp.isCompleted = false
          AND qp.isDeleted = false
        ORDER BY qp.updatedAt DESC
        """
    )
    fun findUncompleted(): List<QuranProgressEntity>


    @Modifying
    @Query(
        value = """
        UPDATE quran_progress
        SET is_deleted = true,
            deleted_at = :deletedAt,
            updated_at = :deletedAt
        WHERE id = :id
        """,
        nativeQuery = true
    )
    fun markAsDeleted(
        @Param("id") id: UUID,
        @Param("deletedAt") deletedAt: Instant
    ): Int


    @Modifying
    @Query(
        value = """
        UPDATE quran_progress
        SET processed_levels = :level,
            is_started = true,
            updated_at = :updatedAt
        WHERE id = :id
        """,
        nativeQuery = true
    )
    fun incrementProgress(
        @Param("id") id: UUID,
        @Param("level") level: Int,
        @Param("updatedAt") updatedAt: Instant
    ): Int


    @Modifying
    @Query(
        value = """
        UPDATE quran_progress
        SET is_completed = true,
            synced_at = :now,
            updated_at = :now
        WHERE id = :id
        """,
        nativeQuery = true
    )
    fun markAsComplete(
        @Param("id") id: UUID,
        @Param("now") now: Instant
    ): Int
}
