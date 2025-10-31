package com.example.demo.data.repository.jpa.referral

import com.example.demo.data.entity.ReferralPathEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReferralJpaRepository : JpaRepository<ReferralPathEntity, UUID> {

    // ✅ Eager load both sides (ancestor & descendant users) to prevent N+1
    @Query("""
        SELECT r
        FROM ReferralPathEntity r
        JOIN FETCH r.ancestor a
        JOIN FETCH r.descendant d
        ORDER BY r.level ASC
    """)
    fun findAllReferrals(): List<ReferralPathEntity>

    // ✅ Existence check - optimized single query
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
        FROM ReferralPathEntity r
        WHERE r.ancestor.id = :ancestor AND r.descendant.id = :descendant
    """)
    fun existsByAncestorAndDescendant(
        @Param("ancestor") ancestor: UUID,
        @Param("descendant") descendant: UUID
    ): Boolean

    // ✅ Cycle detection
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
        FROM ReferralPathEntity r
        WHERE r.ancestor.id = :descendant AND r.descendant.id = :ancestor
    """)
    fun isCycleExists(
        @Param("ancestor") ancestor: UUID,
        @Param("descendant") descendant: UUID
    ): Boolean

    // ✅ Fetch ancestors (light query — IDs only)
    @Query("""
        SELECT r.ancestor.id, r.level
        FROM ReferralPathEntity r
        WHERE r.descendant.id = :descendant
    """)
    fun findAncestorsByDescendant(@Param("descendant") descendant: UUID): List<Array<Any>>

    // ✅ Fetch descendants (light query — IDs only)
    @Query("""
        SELECT r.descendant.id, r.level
        FROM ReferralPathEntity r
        WHERE r.ancestor.id = :ancestor
    """)
    fun findDescendantsByAncestor(@Param("ancestor") ancestor: UUID): List<Array<Any>>

    // ✅ Native delete for efficiency
    @Modifying
    @Query(
        value = "DELETE FROM referral_paths WHERE ancestor = :ancestor AND descendant = :descendant",
        nativeQuery = true
    )
    fun deleteReferral(
        @Param("ancestor") ancestor: UUID,
        @Param("descendant") descendant: UUID
    ): Int

    // ✅ Tree queries with eager users
    @Query("""
        SELECT r
        FROM ReferralPathEntity r
        JOIN FETCH r.ancestor a
        JOIN FETCH r.descendant d
        WHERE r.descendant.id = :userId
        ORDER BY r.level ASC
    """)
    fun findReferralTreeUp(@Param("userId") userId: UUID): List<ReferralPathEntity>

    @Query("""
        SELECT r
        FROM ReferralPathEntity r
        JOIN FETCH r.ancestor a
        JOIN FETCH r.descendant d
        WHERE r.ancestor.id = :userId
        ORDER BY r.level ASC
    """)
    fun findReferralTreeDown(@Param("userId") userId: UUID): List<ReferralPathEntity>
}
