// 🧩 ReferralJpaRepository.kt
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

    @Query("SELECT r FROM ReferralPathEntity r")
    fun findAllReferrals(): List<ReferralPathEntity>

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM ReferralPathEntity r WHERE r.ancestor.id = :ancestor AND r.descendant.id = :descendant")
    fun existsByAncestorAndDescendant(
        @Param("ancestor") ancestor: UUID,
        @Param("descendant") descendant: UUID
    ): Boolean


    @Query("""
    SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
    FROM ReferralPathEntity r
    WHERE r.ancestor.id = :descendant AND r.descendant.id = :ancestor
""")
    fun isCycleExists(@Param("ancestor") ancestor: UUID, @Param("descendant") descendant: UUID): Boolean



    @Query("SELECT r.ancestor.id, r.level FROM ReferralPathEntity r WHERE r.descendant.id = :descendant")
    fun findAncestorsByDescendant(@Param("descendant") descendant: UUID): List<Array<Any>>

    @Query("SELECT r.descendant.id, r.level FROM ReferralPathEntity r WHERE r.ancestor.id = :ancestor")
    fun findDescendantsByAncestor(@Param("ancestor") ancestor: UUID): List<Array<Any>>

    @Modifying
    @Query("DELETE FROM ReferralPathEntity r WHERE r.ancestor.id = :ancestor AND r.descendant.id = :descendant")
    fun deleteReferral(
        @Param("ancestor") ancestor: UUID,
        @Param("descendant") descendant: UUID
    ): Int

    @Query("SELECT r FROM ReferralPathEntity r WHERE r.descendant.id = :userId ORDER BY r.level ASC")
    fun findReferralTreeUp(@Param("userId") userId: UUID): List<ReferralPathEntity>

    @Query("SELECT r FROM ReferralPathEntity r WHERE r.ancestor.id = :userId ORDER BY r.level ASC")
    fun findReferralTreeDown(@Param("userId") userId: UUID): List<ReferralPathEntity>
}
