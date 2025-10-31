package com.example.demo.data.repository.jpa.zikr

import com.example.demo.data.entity.ZikrCollectionMapEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface ZikrCollectionMapJpaRepository : JpaRepository<ZikrCollectionMapEntity, UUID> {

    // ✅ Eager loading — removes N+1 issue
    @Query("""
        SELECT zcm
        FROM ZikrCollectionMapEntity zcm
        JOIN FETCH zcm.zikr z
        JOIN FETCH zcm.collection c
        WHERE zcm.isDeleted = false
        ORDER BY c.orderIndex, zcm.orderIndex ASC
    """)
    fun findAllActive(): List<ZikrCollectionMapEntity>

    // ✅ Consistent eager fetch for incremental syncs
    @Query("""
        SELECT zcm
        FROM ZikrCollectionMapEntity zcm
        JOIN FETCH zcm.zikr z
        JOIN FETCH zcm.collection c
        WHERE zcm.updatedAt > :updatedAt
          AND zcm.isDeleted = false
        ORDER BY zcm.updatedAt DESC
    """)
    fun findUpdatedAfter(updatedAt: Instant): List<ZikrCollectionMapEntity>

    // ✅ Safe & fast soft delete
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = """
            UPDATE zikr_collection_map 
            SET is_deleted = true, deleted_at = :deletedAt 
            WHERE id = :id
        """, nativeQuery = true
    )
    fun markAsDeleted(id: UUID, deletedAt: Instant): Int
}
