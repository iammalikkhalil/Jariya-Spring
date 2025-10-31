package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrRewardJpaRepository
import com.example.demo.domain.model.zikr.ZikrRewardModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrRewardRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.system.measureTimeMillis

@Repository
class ZikrRewardRepositoryImpl(
    private val zikrRewardJpaRepository: ZikrRewardJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrRewardRepository {

    // ✅ Single eager-loaded query — optimized
    @Transactional(readOnly = true)
    override fun getAllZikrRewards(): List<ZikrRewardModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching all ZikrRewards (JOIN FETCH)...")

        lateinit var result: List<ZikrRewardModel>
        val dbTime = measureTimeMillis {
            val entities = zikrRewardJpaRepository.findAllActive()
            result = entities.asSequence().map { it.toModel() }.toList()
        }

        Log.info("✅ getAllZikrRewards completed in ${System.currentTimeMillis() - start}ms (DB=$dbTime ms)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getZikrRewardById(id: String): ZikrRewardModel? {
        return zikrRewardJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikrReward(zikrReward: ZikrRewardModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrReward.zikrId.toUUID()).orElse(null)
            if (zikrEntity == null) {
                Log.warn("⚠️ Zikr not found for ID ${zikrReward.zikrId}")
                return false
            }

            zikrRewardJpaRepository.save(zikrReward.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_reward")
            Log.info("✅ Created ZikrReward: ${zikrReward.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrReward: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateZikrReward(zikrReward: ZikrRewardModel): Boolean {
        return try {
            if (!zikrRewardJpaRepository.existsById(zikrReward.id.toUUID())) return false

            val zikrEntity = zikrJpaRepository.findById(zikrReward.zikrId.toUUID()).orElse(null)
            if (zikrEntity == null) {
                Log.warn("⚠️ Zikr not found for ID ${zikrReward.zikrId}")
                return false
            }

            zikrRewardJpaRepository.save(zikrReward.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_reward")
            Log.info("✅ Updated ZikrReward: ${zikrReward.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrReward: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteZikrReward(id: String): Boolean {
        return try {
            val deleted = zikrRewardJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_reward")
                Log.info("🗑 Soft-deleted ZikrReward: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrReward: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true)
    override fun getUpdatedZikrRewards(updatedAt: Instant): List<ZikrRewardModel> {
        val start = System.currentTimeMillis()
        val result = zikrRewardJpaRepository.findUpdatedAfter(updatedAt).map { it.toModel() }
        Log.info("✅ getUpdatedZikrRewards fetched ${result.size} records in ${System.currentTimeMillis() - start}ms")
        return result
    }
}
