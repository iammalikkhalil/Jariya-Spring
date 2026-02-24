package com.example.demo.presentation.service.progress

import com.example.demo.domain.repository.progress.ZikrPointRepository
import com.example.demo.domain.repository.zikr.ZikrGoalRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.presentation.dto.progress.ZikrPointDto
import com.example.demo.presentation.dto.progress.ZikrPointRequestDto
import com.example.demo.presentation.dto.progress.toDomain
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ZikrPointService(
    private val zikrPointRepository: ZikrPointRepository,
    private val zikrGoalRepository: ZikrGoalRepository
) {

    fun getAllZikrPoints() = zikrPointRepository.getAllZikrPoints()

    fun getZikrPointById(id: String) = zikrPointRepository.getZikrPointById(id)

    fun createZikrPoint(body: ZikrPointRequestDto): Boolean {
        val dto = ZikrPointDto(
            id = body.id,
            zikrId = body.zikrId,
            userId = body.userId,
            progressId = body.progressId,
            progressType = "zikr",
            points = body.points,
            pointsSourceType = body.pointsSourceType,
            sourceUser = body.sourceUser,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt,
            level = body.level,
        )
        return zikrPointRepository.createZikrPoint(dto.toDomain())
    }

    fun updateZikrPoint(body: ZikrPointRequestDto): Boolean {
        val dto = ZikrPointDto(
            id = body.id,
            zikrId = body.zikrId,
            userId = body.userId,
            points = body.points,
            pointsSourceType = body.pointsSourceType,
            sourceUser = body.sourceUser,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt,
            progressId = body.progressId,
            progressType = body.progressType,
            level = body.level,
        )
        return zikrPointRepository.updateZikrPoint(dto.toDomain())
    }

    fun deleteZikrPoint(id: String) = zikrPointRepository.deleteZikrPoint(id)

    fun getZikrPointSummary(id: String) = zikrPointRepository.getZikrPointsSummary(id)

    fun getZikrPointTotal() = zikrPointRepository.getZikrPointsTotal()

    fun getLeaderboard() = zikrPointRepository.getLeaderboard()

    fun getZikrLeaderboard() = zikrPointRepository.getZikrLeaderboard()


    @Transactional
    fun recalculateAndUpdateGoalGlobalTargetValues() {

        val specialGoalId =
            UUID.fromString("2ce601a8-ad80-4ca8-9cf7-f8891f453be6")

        val summaries =
            zikrPointRepository.getAggregatedZikrGoalPointsSummary()

        if (summaries.isEmpty()) {
            Log.info("ℹ No goal summaries found. Skipping recalculation.")
            return
        }

        val updates = mutableMapOf<UUID, Long>()

        summaries.forEach { summary ->

            val goalId = summary.goalId
            val points = summary.totalPoints
            val currentTarget = summary.originalTargetCount

            if (points > currentTarget) {

                val increment =
                    if (goalId == specialGoalId) 100_000L else 10_000L

                val difference = points - currentTarget

                val stepsNeeded =
                    (difference / increment) + 1

                val newTarget =
                    currentTarget + (stepsNeeded * increment)

                updates[goalId] = newTarget

                Log.info(
                    "📈 Goal $goalId target increased from $currentTarget to $newTarget " +
                            "(points=$points, increment=$increment)"
                )
            }
        }

        if (updates.isEmpty()) {
            Log.info("✅ All goals already satisfy target conditions. No updates required.")
            return
        }

        val updatedCount =
            zikrGoalRepository.bulkUpdateGoalTargetValues(updates)

        if (updatedCount == updates.size) {
            Log.info("✅ Successfully updated $updatedCount goal target values.")
        } else {
            Log.error(
                "⚠ Bulk update mismatch. Expected=${updates.size}, Updated=$updatedCount"
            )
        }
    }

}