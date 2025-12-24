package com.example.demo.data.repository.impl.progress

import com.example.demo.data.entity.ZikrPointEntity
import com.example.demo.data.mapper.progress.toEntity
import com.example.demo.data.mapper.progress.toModel
import com.example.demo.data.repository.jpa.auth.UserJpaRepository
import com.example.demo.data.repository.jpa.progress.GoalProgressJpaRepository
import com.example.demo.data.repository.jpa.progress.ZikrPointJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrGoalJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.domain.model.progress.GoalProgressModel
import com.example.demo.domain.repository.progress.GoalProgressRepository
import com.example.demo.domain.repository.referral.ReferralRepository
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class GoalProgressRepositoryImpl(
    private val goalProgressJpaRepository: GoalProgressJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val zikrGoalJpaRepository: ZikrGoalJpaRepository,
    private val zikrPointJpaRepository: ZikrPointJpaRepository,
    private val userJpaRepository: UserJpaRepository,
    private val referralRepository: ReferralRepository,
    private val syncLogRepository: SyncLogRepository
) : GoalProgressRepository {

    @Transactional(readOnly = true)
    override fun getAllGoalProgresses(): List<GoalProgressModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching GoalProgress records with JOIN FETCH...")
        val result = goalProgressJpaRepository.findAllActive().map { it.toModel() }
        Log.info("✅ getAllGoalProgresses completed in ${System.currentTimeMillis() - start}ms (${result.size} records)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getGoalProgressById(id: String): GoalProgressModel? {
        return goalProgressJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createGoalProgress(goalProgress: GoalProgressModel): Boolean {
        return try {
            val user = userJpaRepository.findById(goalProgress.userId.toUUID()).orElse(null)
            if (user == null) {
                Log.error("❌ User not found: ${goalProgress.userId}")
                return false
            }

            val zikr = zikrJpaRepository.findById(goalProgress.zikrId.toUUID()).orElse(null)
            if (zikr == null) {
                Log.error("❌ Zikr not found: ${goalProgress.zikrId}")
                return false
            }


            val goal = zikrGoalJpaRepository.findById(goalProgress.goalId.toUUID()).orElse(null)
            if (goal == null) {
                Log.error("❌ Zikr Goal not found: ${goalProgress.goalId}")
                return false
            }

            val progress = goalProgress.toEntity(user, zikr, goal)
            goalProgressJpaRepository.save(progress)

            val now = Instant.now()
            val tree = referralRepository.getReferralTreeUp(goalProgress.userId)
            val basePoints = goalProgress.count * 10 * zikr.charCount

            val points = mutableListOf<ZikrPointEntity>().apply {
                add(
                    ZikrPointEntity(
                        id = generateUUID().toUUID(),
                        user = user,
                        progressType = "goal",
                        progressId = progress.id.toString(),
                        sourceUser = user,
                        zikr = zikr,
                        level = 0,
                        points = basePoints,
                        sourceType = "ZIKR",
                        createdAt = now,
                        updatedAt = now
                    )
                )

                tree.forEach { dto ->
                    userJpaRepository.findById(dto.ancestor.toUUID()).ifPresent { ancestor ->
                        add(
                            ZikrPointEntity(
                                id = generateUUID().toUUID(),
                                user = ancestor,
                                sourceUser = user,
                                zikr = zikr,
                                progressType = "goal",
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
            }

            zikrPointJpaRepository.saveAll(points)
            syncLogRepository.updateSyncLog("zikr_progress")

            Log.info("✅ GoalProgress + ${points.size} points created for user ${goalProgress.userId}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating GoalProgress: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateGoalProgress(goalProgress: GoalProgressModel): Boolean {
        return try {
            if (!goalProgressJpaRepository.existsById(goalProgress.id.toUUID())) return false

            val user = userJpaRepository.findById(goalProgress.userId.toUUID()).orElse(null)
            if (user == null) {
                Log.error("❌ User not found for update: ${goalProgress.userId}")
                return false
            }

            val zikr = zikrJpaRepository.findById(goalProgress.zikrId.toUUID()).orElse(null)
            if (zikr == null) {
                Log.error("❌ Zikr not found for update: ${goalProgress.zikrId}")
                return false
            }

            val goal = zikrGoalJpaRepository.findById(goalProgress.goalId.toUUID()).orElse(null)
            if (goal == null) {
                Log.error("❌ Zikr Goal not found for update: ${goalProgress.goalId}")
                return false
            }


            goalProgressJpaRepository.save(goalProgress.toEntity(user, zikr, goal))
            syncLogRepository.updateSyncLog("zikr_progress")

            Log.info("✅ Updated GoalProgress: ${goalProgress.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating GoalProgress: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteGoalProgress(id: String): Boolean {
        return try {
            val deleted = goalProgressJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_progress")
                Log.info("🗑 Soft deleted GoalProgress: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting GoalProgress: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true)
    override fun getUncompletedRecords(): List<GoalProgressModel> {
        return goalProgressJpaRepository.findUncompleted().map { it.toModel() }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun incrementGoalProgress(id: String, level: Int): Boolean {
        return try {
            goalProgressJpaRepository.incrementProgress(id.toUUID(), level, Instant.now()) > 0
        } catch (e: Exception) {
            Log.error("❌ Error incrementing progress: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun markGoalProgressAsComplete(id: String): Boolean {
        return try {
            goalProgressJpaRepository.markAsComplete(id.toUUID(), Instant.now()) > 0
        } catch (e: Exception) {
            Log.error("❌ Error marking progress complete: ${e.message}", e)
            false
        }
    }
}