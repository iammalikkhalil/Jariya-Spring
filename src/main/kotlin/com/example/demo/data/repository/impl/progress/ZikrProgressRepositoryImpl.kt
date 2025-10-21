package com.example.demo.data.repository.impl.progress

import com.example.demo.data.entity.ZikrPointEntity
import com.example.demo.data.mapper.progress.toEntity
import com.example.demo.data.mapper.progress.toModel
import com.example.demo.data.repository.jpa.auth.UserJpaRepository
import com.example.demo.data.repository.jpa.progress.ZikrPointJpaRepository
import com.example.demo.data.repository.jpa.progress.ZikrProgressJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.domain.model.progress.ZikrProgressModel
import com.example.demo.domain.repository.progress.ZikrProgressRepository
import com.example.demo.domain.repository.referral.ReferralRepository
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant


@Repository
class ZikrProgressRepositoryImpl(
    private val zikrProgressJpaRepository: ZikrProgressJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val zikrPointJpaRepository: ZikrPointJpaRepository,
    private val userJpaRepository: UserJpaRepository,
    private val referralRepository: ReferralRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrProgressRepository {

    override fun getAllZikrProgresses(): List<ZikrProgressModel> =
        zikrProgressJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrProgressById(id: String): ZikrProgressModel? =
        zikrProgressJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun createZikrProgress(zikrProgress: ZikrProgressModel): Boolean {
        return try {
            val userEntity = userJpaRepository.findById(zikrProgress.userId.toUUID()).orElse(null)
                ?: return false.also { Log.error("❌ User not found for ID: ${zikrProgress.userId}") }

            val zikrEntity = zikrJpaRepository.findById(zikrProgress.zikrId.toUUID()).orElse(null)
                ?: return false.also { Log.error("❌ Zikr not found for ID: ${zikrProgress.zikrId}") }

            val progressEntity = zikrProgress.toEntity(userEntity, zikrEntity)
            zikrProgressJpaRepository.save(progressEntity)

            val tree = referralRepository.getReferralTreeUp(zikrProgress.userId)
            val now = Instant.now()
            val zikrPoints = mutableListOf<ZikrPointEntity>()

            val basePoints = zikrProgress.count * 10 * zikrEntity.charCount

            // 🔹 Primary user point
            zikrPoints.add(
                ZikrPointEntity(
                    id = generateUUID().toUUID(),
                    zikr = zikrEntity,
                    user = userEntity,
                    progress = progressEntity,
                    level = 0,
                    points = basePoints,
                    sourceType = "ZIKR",
                    sourceUser = userEntity,
                    createdAt = now,
                    updatedAt = now,
                    isDeleted = false
                )
            )

            // 🔹 Referral points
            tree.forEach { dto ->
                val ancestorUser = userJpaRepository.findById(dto.ancestor.toUUID()).orElse(null)
                if (ancestorUser != null) {
                    zikrPoints.add(
                        ZikrPointEntity(
                            id = generateUUID().toUUID(),
                            zikr = zikrEntity,
                            user = ancestorUser,
                            progress = progressEntity,
                            level = dto.level,
                            points = basePoints,
                            sourceType = "REFERRAL",
                            sourceUser = userEntity,
                            createdAt = now,
                            updatedAt = now,
                            isDeleted = false
                        )
                    )
                }
            }

            zikrPointJpaRepository.saveAll(zikrPoints)
            syncLogRepository.updateSyncLog("zikr_progress")

            Log.info("✅ ZikrProgress + ${zikrPoints.size} points created for user ${zikrProgress.userId}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrProgress: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun updateZikrProgress(zikrProgress: ZikrProgressModel): Boolean {
        return try {
            if (!zikrProgressJpaRepository.existsById(zikrProgress.id.toUUID())) return false
            val userEntity = userJpaRepository.findById(zikrProgress.userId.toUUID()).orElse(null) ?: return false
            val zikrEntity = zikrJpaRepository.findById(zikrProgress.zikrId.toUUID()).orElse(null) ?: return false
            zikrProgressJpaRepository.save(zikrProgress.toEntity(userEntity, zikrEntity))
            syncLogRepository.updateSyncLog("zikr_progress")
            Log.info("✅ Updated ZikrProgress: ${zikrProgress.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrProgress: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun deleteZikrProgress(id: String): Boolean {
        return try {
            val deleted = zikrProgressJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_progress")
                Log.info("✅ Soft-deleted ZikrProgress: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrProgress: ${e.message}", e)
            false
        }
    }

    override fun getUncompletedRecords(): List<ZikrProgressModel> =
        zikrProgressJpaRepository.findAllByIsCompletedFalse().map { it.toModel() }

    @Transactional
    override fun incrementZikrProgress(id: String, level: Int): Boolean {
        return try {
            zikrProgressJpaRepository.incrementProgress(id.toUUID(), level, Instant.now()) > 0
        } catch (e: Exception) {
            Log.error("❌ Error incrementing ZikrProgress: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun markZikrProgressAsComplete(id: String): Boolean {
        return try {
            zikrProgressJpaRepository.markAsComplete(id.toUUID(), Instant.now()) > 0
        } catch (e: Exception) {
            Log.error("❌ Error marking ZikrProgress complete: ${e.message}", e)
            false
        }
    }
}
