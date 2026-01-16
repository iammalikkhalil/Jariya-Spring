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

    // -------------------- READ --------------------

    @Transactional(readOnly = true)
    override fun getAllZikrProgresses(): List<ZikrProgressModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching ZikrProgress records...")
        val result = zikrProgressJpaRepository.findAllActive().map { it.toModel() }
        Log.info("✅ getAllZikrProgresses completed in ${System.currentTimeMillis() - start}ms (${result.size} records)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getZikrProgressById(id: String): ZikrProgressModel? {
        return zikrProgressJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()
    }

    // -------------------- CREATE --------------------

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikrProgress(zikrProgress: ZikrProgressModel): Boolean {
        return try {
            val userId = zikrProgress.userId

            val user = userJpaRepository.findById(userId.toUUID()).orElse(null)
            if (user == null) {
                Log.error("❌ User not found: $userId")
                return false
            }

            val zikr = zikrJpaRepository.findById(zikrProgress.zikrId.toUUID()).orElse(null)
            if (zikr == null) {
                Log.error("❌ Zikr not found: ${zikrProgress.zikrId}")
                return false
            }

            // ✅ ZikrProgress still uses UserEntity FK
            val progress = zikrProgress.toEntity(user, zikr)
            zikrProgressJpaRepository.save(progress)

            val now = Instant.now()
            val basePoints = zikrProgress.count * 10 * zikr.charCount

            val tree = referralRepository.getReferralTreeUp(userId)

            val points = mutableListOf<ZikrPointEntity>().apply {

                // ✅ ZIKR points (store userId as String)
                add(
                    ZikrPointEntity(
                        id = generateUUID().toUUID(),
                        user = userId,
                        sourceUser = userId,
                        zikr = zikr,
                        progressType = "zikr",
                        progressId = progress.id.toString(),
                        level = 0,
                        points = basePoints,
                        sourceType = "ZIKR",
                        createdAt = now,
                        updatedAt = now
                    )
                )

                // ✅ Referral points (store ancestorId as String)
                tree.forEach { dto ->
                    add(
                        ZikrPointEntity(
                            id = generateUUID().toUUID(),
                            user = dto.ancestor,
                            sourceUser = userId,
                            zikr = zikr,
                            progressType = "zikr",
                            progressId = progress.id.toString(),
                            level = dto.level,
                            points = basePoints,
                            sourceType = "REFERRAL",
                            createdAt = now,
                            updatedAt = now
                        )
                    )
                }
            }

            zikrPointJpaRepository.saveAll(points)
            syncLogRepository.updateSyncLog("zikr_progress")

            Log.info("✅ ZikrProgress + ${points.size} points created for user $userId")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrProgress: ${e.message}", e)
            false
        }
    }

    // -------------------- UPDATE --------------------

    @Transactional(rollbackFor = [Exception::class])
    override fun updateZikrProgress(zikrProgress: ZikrProgressModel): Boolean {
        return try {
            if (!zikrProgressJpaRepository.existsById(zikrProgress.id.toUUID())) return false

            val user = userJpaRepository.findById(zikrProgress.userId.toUUID()).orElse(null)
            if (user == null) {
                Log.error("❌ User not found for update: ${zikrProgress.userId}")
                return false
            }

            val zikr = zikrJpaRepository.findById(zikrProgress.zikrId.toUUID()).orElse(null)
            if (zikr == null) {
                Log.error("❌ Zikr not found for update: ${zikrProgress.zikrId}")
                return false
            }

            // ✅ Still uses UserEntity FK
            zikrProgressJpaRepository.save(zikrProgress.toEntity(user, zikr))
            syncLogRepository.updateSyncLog("zikr_progress")

            Log.info("✅ Updated ZikrProgress: ${zikrProgress.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrProgress: ${e.message}", e)
            false
        }
    }

    // -------------------- DELETE --------------------

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteZikrProgress(id: String): Boolean {
        return try {
            val deleted = zikrProgressJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_progress")
                Log.info("🗑 Soft deleted ZikrProgress: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrProgress: ${e.message}", e)
            false
        }
    }

    // -------------------- EXTRA --------------------

    @Transactional(readOnly = true)
    override fun getUncompletedRecords(): List<ZikrProgressModel> {
        return zikrProgressJpaRepository.findUncompleted().map { it.toModel() }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun incrementZikrProgress(id: String, level: Int): Boolean {
        return try {
            zikrProgressJpaRepository.incrementProgress(id.toUUID(), level, Instant.now()) > 0
        } catch (e: Exception) {
            Log.error("❌ Error incrementing progress: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun markZikrProgressAsComplete(id: String): Boolean {
        return try {
            zikrProgressJpaRepository.markAsComplete(id.toUUID(), Instant.now()) > 0
        } catch (e: Exception) {
            Log.error("❌ Error marking progress complete: ${e.message}", e)
            false
        }
    }
}
