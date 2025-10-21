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

    override fun getAllReferrals(): List<ReferralModel> =
        referralJpaRepository.findAll().map { it.toModel() }

    /**
     * Checks if a referral path already exists or would create a cycle.
     */
    override fun isAlreadyExists(ancestor: String, descendant: String): Boolean {
        val a = ancestor.toUUID()
        val d = descendant.toUUID()

        if (a == d) return true // self-referral

        // Single DB check covers both direct and reverse link
        val exists = referralJpaRepository.existsByAncestorAndDescendant(a, d)
                || referralJpaRepository.isCycleExists(a, d)

        if (exists)
            Log.warn("⛔ Cycle or duplicate referral detected between $ancestor → $descendant")

        return exists
    }

    /**
     * Adds a new referral path (atomic & optimized).
     */
    @Transactional
    override fun addReferral(ancestor: String, descendant: String): Boolean {

   Log.info("Data Layer Running....")

        val start = System.currentTimeMillis()
        val ancestorId = ancestor.toUUID()
        val descendantId = descendant.toUUID()

//        if (ancestorId == descendantId)
//            throw AppException(HttpStatus.BAD_REQUEST, "Self-referral is not allowed")

//        try {
            val users = userJpaRepository.findAllById(listOf(ancestorId, descendantId)).associateBy { it.id }
            val ancestorUser = users[ancestorId]
            val descendantUser = users[descendantId]

            if (ancestorUser == null || descendantUser == null)
                throw AppException(HttpStatus.NOT_FOUND, "Invalid ancestor or descendant reference")

            if (descendantUser.referredBy != null)
                throw AppException(HttpStatus.CONFLICT, "User is already referred by someone")

            val duplicate = referralJpaRepository.existsByAncestorAndDescendant(ancestorId, descendantId)
            val cycle = referralJpaRepository.isCycleExists(ancestorId, descendantId)
            if (duplicate || cycle)
                throw AppException(HttpStatus.CONFLICT, "Cycle or duplicate referral detected")

            // Update user referral
            descendantUser.referredBy = ancestorUser.id
            descendantUser.updatedAt = Instant.now()
            userJpaRepository.save(descendantUser)

            // Build tree connections
            val ancestors = referralJpaRepository.findAncestorsByDescendant(ancestorId)
                .mapNotNull { (it[0] as? UUID)?.let { id -> id to (it[1] as Int) } }
            val descendants = referralJpaRepository.findDescendantsByAncestor(descendantId)
                .mapNotNull { (it[0] as? UUID)?.let { id -> id to (it[1] as Int) } }

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
//        } catch (e: AppException) {
//            throw e
//        } catch (e: Exception) {
//            Log.error("💥 Repository error: ${e.message}", e)
//            throw AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected DB error while adding referral")
//        }
    }

    override fun getReferralTreeUp(ancestor: String): List<ReferralModel> =
        referralJpaRepository.findReferralTreeUp(ancestor.toUUID()).map { it.toModel() }

    override fun getReferralTreeDown(descendant: String): List<ReferralModel> =
        referralJpaRepository.findReferralTreeDown(descendant.toUUID()).map { it.toModel() }
}