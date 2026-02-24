package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.repository.jpa.zikr.ZikrGoalJpaRepository
import com.example.demo.domain.exception.AppException
import com.example.demo.domain.model.zikr.ZikrGoalModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrGoalRepository
import com.example.demo.infrastructure.constants.MessageConstants
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Repository
class ZikrGoalRepositoryImpl(
    private val zikrGoalJpaRepository: ZikrGoalJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrGoalRepository {

    @Transactional(readOnly = true)
    override fun getAllGoals(): List<ZikrGoalModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching ZikrGoals...")

        val result = zikrGoalJpaRepository.findAllActive().map { it.toModel() }

        Log.info("✅ getAllGoals completed in ${System.currentTimeMillis() - start} ms (${result.size} records)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getGoalById(id: String): ZikrGoalModel? {
        return zikrGoalJpaRepository
            .findById(id.toUUID())
            .orElse(null)
            ?.takeIf { it.deletedAt == null }
            ?.toModel()
    }

    @Transactional
    override fun createGoal(goal: ZikrGoalModel): Boolean {
        val id = goal.id?.toUUID()

        // 🔒 Create with ID: reject if already exists
        if (id != null && zikrGoalJpaRepository.existsById(id)) {
            throw AppException(
                HttpStatus.CONFLICT,
                MessageConstants.Data.CONFLICT
            )
        }

        zikrGoalJpaRepository.save(goal.toEntity())
        syncLogRepository.updateSyncLog("zikr_goal")

        Log.info("✅ Created ZikrGoal: ${goal.id}")
        return true
    }

    @Transactional
    override fun updateGoal(goal: ZikrGoalModel): Boolean {
        val id = goal.id.toUUID()

        val existing = zikrGoalJpaRepository.findById(id)
            .orElseThrow {
                AppException(
                    HttpStatus.NOT_FOUND,
                    MessageConstants.Data.NOT_FOUND
                )
            }

        // ❌ soft-deleted → reject update
        if (existing.deletedAt != null) {
            throw AppException(
                HttpStatus.CONFLICT,
                MessageConstants.Data.DELETED
            )
        }

        zikrGoalJpaRepository.save(goal.toEntity())
        syncLogRepository.updateSyncLog("zikr_goal")

        Log.info("✅ Updated ZikrGoal: ${goal.id}")
        return true
    }

    @Transactional
    override fun deleteGoal(id: String): Boolean {
        val affected = zikrGoalJpaRepository.markAsDeleted(id.toUUID(), Instant.now())

        if (affected == 0) {
            throw AppException(
                HttpStatus.NOT_FOUND,
                MessageConstants.Data.NOT_FOUND
            )
        }

        syncLogRepository.updateSyncLog("zikr_goal")
        Log.info("🗑 Soft-deleted ZikrGoal: $id")
        return true
    }

    @Transactional(readOnly = true)
    override fun getUpdatedGoals(updatedAt: Instant): List<ZikrGoalModel> {
        val start = System.currentTimeMillis()

        val result = zikrGoalJpaRepository
            .findUpdatedAfter(updatedAt)
            .map { it.toModel() }

        Log.info("✅ getUpdatedGoals fetched ${result.size} records in ${System.currentTimeMillis() - start} ms")
        return result
    }

    @Transactional
    override fun bulkUpdateGoalTargetValues(
        updates: Map<UUID, Long>
    ): Int {

        var totalUpdated = 0
        val now = Instant.now()

        updates.forEach { (goalId, finalTargetCount) ->
            val affected = zikrGoalJpaRepository
                .updateGlobalTargetValueById(goalId, finalTargetCount, now)

            totalUpdated += affected
        }

        if (totalUpdated > 0) {
            syncLogRepository.updateSyncLog("zikr_goal")
            syncLogRepository.updateSyncLog("zikr_goal_map")
        }

        Log.info("✅ Updated globalTargetValue for $totalUpdated goals")

        return totalUpdated
    }

}


