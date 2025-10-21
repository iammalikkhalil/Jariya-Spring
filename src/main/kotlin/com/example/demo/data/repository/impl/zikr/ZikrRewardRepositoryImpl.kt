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

@Repository
class ZikrRewardRepositoryImpl(
    private val zikrRewardJpaRepository: ZikrRewardJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrRewardRepository {

    override fun getAllZikrRewards(): List<ZikrRewardModel> =
        zikrRewardJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrRewardById(id: String): ZikrRewardModel? =
        zikrRewardJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun createZikrReward(zikrReward: ZikrRewardModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrReward.zikrId.toUUID()).orElse(null) ?: return false
            zikrRewardJpaRepository.save(zikrReward.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_reward")
            Log.info("✅ Created ZikrReward: ${zikrReward.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrReward: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun updateZikrReward(zikrReward: ZikrRewardModel): Boolean {
        return try {
            if (!zikrRewardJpaRepository.existsById(zikrReward.id.toUUID())) return false
            val zikrEntity = zikrJpaRepository.findById(zikrReward.zikrId.toUUID()).orElse(null) ?: return false
            zikrRewardJpaRepository.save(zikrReward.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_reward")
            Log.info("✅ Updated ZikrReward: ${zikrReward.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrReward: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun deleteZikrReward(id: String): Boolean {
        return try {
            val deleted = zikrRewardJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_reward")
                Log.info("✅ Soft-deleted ZikrReward: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrReward: ${e.message}", e)
            false
        }
    }

    override fun getUpdatedZikrRewards(updatedAt: Instant): List<ZikrRewardModel> =
        zikrRewardJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }
}
