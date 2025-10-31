package com.example.demo.data.repository.impl.referral

import com.example.demo.data.entity.ReferralPathEntity
import com.example.demo.data.entity.ReferralPathId
import com.example.demo.data.mapper.referral.toModel
import com.example.demo.data.repository.jpa.auth.UserJpaRepository
import com.example.demo.data.repository.jpa.referral.ReferralJpaRepository
import com.example.demo.domain.exception.AppException
import com.example.demo.domain.model.referral.ReferralModel
import com.example.demo.domain.repository.referral.ReferralRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
class ReferralRepositoryImpl(
    private val referralJpaRepository: ReferralJpaRepository,
    private val userJpaRepository: UserJpaRepository
) : ReferralRepository {

    // ✅ Eager fetch (JOIN FETCH already in repository)
    override fun getAllReferrals(): List<ReferralModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching referral paths...")
        val result = referralJpaRepository.findAllReferrals().map { it.toModel() }
        Log.info("✅ getAllReferrals completed in ${System.currentTimeMillis() - start}ms (${result.size} records)")
        return result
    }

    override fun isAlreadyExists(ancestor: String, descendant: String): Boolean {
        val a = ancestor.toUUID()
        val d = descendant.toUUID()
        if (a == d) return true

        val exists = referralJpaRepository.existsByAncestorAndDescendant(a, d)
                || referralJpaRepository.isCycleExists(a, d)

        if (exists)
            Log.warn("⛔ Cycle or duplicate referral detected between $ancestor → $descendant")

        return exists
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun addReferral(ancestor: String, descendant: String): Boolean {
        val start = System.currentTimeMillis()
        val ancestorId = ancestor.toUUID()
        val descendantId = descendant.toUUID()

        val users = userJpaRepository.findAllById(listOf(ancestorId, descendantId)).associateBy { it.id }
        val ancestorUser = users[ancestorId]
        val descendantUser = users[descendantId]

        if (ancestorUser == null || descendantUser == null)
            throw AppException(HttpStatus.NOT_FOUND, "Invalid ancestor or descendant reference")

        if (descendantUser.referredBy != null)
            throw AppException(HttpStatus.CONFLICT, "User already referred by someone")

        if (referralJpaRepository.existsByAncestorAndDescendant(ancestorId, descendantId) ||
            referralJpaRepository.isCycleExists(ancestorId, descendantId))
            throw AppException(HttpStatus.CONFLICT, "Cycle or duplicate referral detected")

        // ✅ Update user referral relationship
        descendantUser.referredBy = ancestorUser.id
        descendantUser.updatedAt = Instant.now()
        userJpaRepository.save(descendantUser)

        // ✅ Fetch ancestors & descendants (lightweight ID+level)
        val ancestors = referralJpaRepository.findAncestorsByDescendant(ancestorId)
            .mapNotNull { (it[0] as? UUID)?.let { id -> id to (it[1] as Int) } }

        val descendants = referralJpaRepository.findDescendantsByAncestor(descendantId)
            .mapNotNull { (it[0] as? UUID)?.let { id -> id to (it[1] as Int) } }

        // ✅ Prebuild entities in memory (fast & efficient)
        val newPaths = ArrayList<ReferralPathEntity>((ancestors.size + 1) * (descendants.size + 1))
        val fullAncestors = ancestors + (ancestorId to 0)
        val fullDescendants = descendants + (descendantId to 0)

        for ((aId, aLevel) in fullAncestors) {
            for ((dId, dLevel) in fullDescendants) {
                if (aId == dId) continue
                val aUser = users[aId] ?: userJpaRepository.getReferenceById(aId)
                val dUser = users[dId] ?: userJpaRepository.getReferenceById(dId)
                newPaths.add(
                    ReferralPathEntity(
                        id = ReferralPathId(aId, dId),
                        ancestor = aUser,
                        descendant = dUser,
                        level = aLevel + dLevel + 1
                    )
                )
            }
        }

        referralJpaRepository.saveAll(newPaths)
        Log.info("✅ Referral added ${ancestorUser.email} → ${descendantUser.email} (${System.currentTimeMillis() - start}ms)")
        return true
    }

    override fun getReferralTreeUp(ancestor: String): List<ReferralModel> =
        referralJpaRepository.findReferralTreeUp(ancestor.toUUID()).map { it.toModel() }

    override fun getReferralTreeDown(descendant: String): List<ReferralModel> =
        referralJpaRepository.findReferralTreeDown(descendant.toUUID()).map { it.toModel() }
}
