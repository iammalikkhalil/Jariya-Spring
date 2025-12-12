package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toDomain
import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.repository.jpa.zikr.ZikrGoalJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrGoalMapJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.domain.model.zikr.ZikrGoalMapModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrGoalMapRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.system.measureTimeMillis

@Repository
class ZikrGoalMapRepositoryImpl(
    private val zikrGoalMapJpaRepository: ZikrGoalMapJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val zikrGoalJpaRepository: ZikrGoalJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrGoalMapRepository
{

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getAllGoalMaps(): List<ZikrGoalMapModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ [getAllGoalMaps] Fetching ZikrGoalMap records...")

        val entities = zikrGoalMapJpaRepository.findAllActive()
        val result = entities.map { it.toDomain() }

        Log.info("✅ getAllGoalMaps completed in ${System.currentTimeMillis() - start} ms (${result.size})")
        return result
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getGoalMapById(id: String): ZikrGoalMapModel? {
        return zikrGoalMapJpaRepository
            .findById(id.toUUID())
            .orElse(null)
            ?.takeIf { it.deletedAt == null }
            ?.toDomain()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createGoalMap(goalMap: ZikrGoalMapModel): Boolean {
        return try {
            // 🔒 FK: Zikr must exist & not deleted
            val zikr = zikrJpaRepository.findById(goalMap.zikrId.toUUID())
                .orElse(null)
                ?: return false
            if (zikr.deletedAt != null) return false

            // 🔒 FK: Goal must exist & not deleted
            val goal = zikrGoalJpaRepository.findById(goalMap.goalId.toUUID())
                .orElse(null)
                ?: return false
            if (goal.deletedAt != null) return false

            zikrGoalMapJpaRepository.save(
                goalMap.toEntity(zikr, goal)
            )

            syncLogRepository.updateSyncLog("zikr_goal_map")
            Log.info("✅ Created ZikrGoalMap: ${goalMap.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrGoalMap: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateGoalMap(goalMap: ZikrGoalMapModel): Boolean {
        return try {
            val existing = zikrGoalMapJpaRepository
                .findById(goalMap.id.toUUID())
                .orElse(null)
                ?: return false

            if (existing.deletedAt != null) return false

            // 🔒 FK: Zikr check
            val zikr = zikrJpaRepository.findById(goalMap.zikrId.toUUID())
                .orElse(null)
                ?: return false
            if (zikr.deletedAt != null) return false

            // 🔒 FK: Goal check
            val goal = zikrGoalJpaRepository.findById(goalMap.goalId.toUUID())
                .orElse(null)
                ?: return false
            if (goal.deletedAt != null) return false

            zikrGoalMapJpaRepository.save(
                goalMap.toEntity(zikr, goal)
            )

            syncLogRepository.updateSyncLog("zikr_goal_map")
            Log.info("✅ Updated ZikrGoalMap: ${goalMap.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrGoalMap: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteGoalMap(id: String): Boolean {
        return try {
            val affected = zikrGoalMapJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (affected > 0) {
                syncLogRepository.updateSyncLog("zikr_goal_map")
                Log.info("🗑 Soft-deleted ZikrGoalMap: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrGoalMap: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getUpdatedGoalMaps(updatedAt: Instant): List<ZikrGoalMapModel> {
        return zikrGoalMapJpaRepository
            .findUpdatedAfter(updatedAt)
            .map { it.toDomain() }
    }
}

