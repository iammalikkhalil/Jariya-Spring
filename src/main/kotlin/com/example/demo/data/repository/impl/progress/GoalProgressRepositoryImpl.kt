package com.example.demo.data.repository.impl.progress

import com.example.demo.data.entity.GoalProgressEntity
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
import com.example.demo.presentation.dto.sync.GoalProgressSyncDto
import com.example.demo.presentation.dto.sync.SyncAcknowledgeDto
import com.example.demo.presentation.dto.sync.SyncSummaryDto
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import org.slf4j.LoggerFactory
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


    private val log = LoggerFactory.getLogger(GoalProgressRepositoryImpl::class.java)

    // -------------------- READ --------------------

    @Transactional(readOnly = true)
    override fun getAllGoalProgresses(): List<GoalProgressModel> {
        log.info("[REPO] 🔹 🚀 Fetch all goal progress → START")
        val start = System.currentTimeMillis()

        val result = goalProgressJpaRepository.findAllActive().map { it.toModel() }

        log.info(
            "[REPO] ✅ 🔹 Fetch all goal progress → SUCCESS | count={} | timeMs={}",
            result.size,
            System.currentTimeMillis() - start
        )
        return result
    }

    @Transactional(readOnly = true)
    override fun getGoalProgressById(id: String): GoalProgressModel? {
        log.info("[REPO] 🔹 🚀 Fetch goal progress by id → START | id={}", id)

        val result = goalProgressJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

        if (result == null) {
            log.warn("[REPO] ⚠️ 🔹 Fetch goal progress by id → NOT FOUND | id={}", id)
        } else {
            log.info("[REPO] ✅ 🔹 Fetch goal progress by id → SUCCESS | id={}", id)
        }

        return result
    }



    // -------------------- CREATE --------------------

    @Transactional(rollbackFor = [Exception::class])
    override fun createGoalProgress(goalProgress: GoalProgressModel): Boolean {
        log.info(
            "[REPO] 🔹 🚀 Create goal progress → START | userId={} | goalId={}",
            goalProgress.userId,
            goalProgress.goalId
        )

        return try {
            val user = userJpaRepository.findById(goalProgress.userId.toUUID()).orElse(null)
            if (user == null) {
                log.error(
                    "[REPO] 🔴 ❌ Create goal progress → FAILED | reason=UserNotFound | userId={}",
                    goalProgress.userId
                )
                return false
            }

            val zikr = zikrJpaRepository.findById(goalProgress.zikrId.toUUID()).orElse(null)
            if (zikr == null) {
                log.error(
                    "[REPO] 🔴 ❌ Create goal progress → FAILED | reason=ZikrNotFound | zikrId={}",
                    goalProgress.zikrId
                )
                return false
            }

            val goal = zikrGoalJpaRepository.findById(goalProgress.goalId.toUUID()).orElse(null)
            if (goal == null) {
                log.error(
                    "[REPO] 🔴 ❌ Create goal progress → FAILED | reason=GoalNotFound | goalId={}",
                    goalProgress.goalId
                )
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

            log.info(
                "[REPO] ✅ 🔹 Create goal progress → SUCCESS | userId={} | pointsCreated={}",
                goalProgress.userId,
                points.size
            )
            true
        } catch (e: Exception) {
            log.error(
                "[REPO] 🔴 ❌ Create goal progress → FAILED | reason=Exception | message={}",
                e.message,
                e
            )
            false
        }
    }



    // -------------------- UPDATE --------------------

    @Transactional(rollbackFor = [Exception::class])
    override fun updateGoalProgress(goalProgress: GoalProgressModel): Boolean {
        log.info(
            "[REPO] 🔹 🚀 Update goal progress → START | id={}",
            goalProgress.id
        )

        return try {
            if (!goalProgressJpaRepository.existsById(goalProgress.id.toUUID())) {
                log.warn(
                    "[REPO] ⚠️ ⏭️ Update goal progress → SKIPPED | reason=NotFound | id={}",
                    goalProgress.id
                )
                return false
            }

            val user = userJpaRepository.findById(goalProgress.userId.toUUID()).orElse(null)
            if (user == null) {
                log.error(
                    "[REPO] 🔴 ❌ Update goal progress → FAILED | reason=UserNotFound | userId={}",
                    goalProgress.userId
                )
                return false
            }

            val zikr = zikrJpaRepository.findById(goalProgress.zikrId.toUUID()).orElse(null)
            if (zikr == null) {
                log.error(
                    "[REPO] 🔴 ❌ Update goal progress → FAILED | reason=ZikrNotFound | zikrId={}",
                    goalProgress.zikrId
                )
                return false
            }

            val goal = zikrGoalJpaRepository.findById(goalProgress.goalId.toUUID()).orElse(null)
            if (goal == null) {
                log.error(
                    "[REPO] 🔴 ❌ Update goal progress → FAILED | reason=GoalNotFound | goalId={}",
                    goalProgress.goalId
                )
                return false
            }

            goalProgressJpaRepository.save(goalProgress.toEntity(user, zikr, goal))
            syncLogRepository.updateSyncLog("zikr_progress")

            log.info(
                "[REPO] ✅ 🔹 Update goal progress → SUCCESS | id={}",
                goalProgress.id
            )
            true
        } catch (e: Exception) {
            log.error(
                "[REPO] 🔴 ❌ Update goal progress → FAILED | reason=Exception | message={}",
                e.message,
                e
            )
            false
        }
    }



    // -------------------- DELETE --------------------

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteGoalProgress(id: String): Boolean {
        log.info("[REPO] 🔹 🚀 Delete goal progress → START | id={}", id)

        return try {
            val deleted = goalProgressJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_progress")
                log.info(
                    "[REPO] ✅ 🔹 Delete goal progress → SUCCESS | id={}",
                    id
                )
                true
            } else {
                log.warn(
                    "[REPO] ⚠️ ⏭️ Delete goal progress → SKIPPED | reason=NotFound | id={}",
                    id
                )
                false
            }
        } catch (e: Exception) {
            log.error(
                "[REPO] 🔴 ❌ Delete goal progress → FAILED | reason=Exception | message={}",
                e.message,
                e
            )
            false
        }
    }

    // -------------------- BULK SYNC --------------------

        @Transactional(rollbackFor = [Exception::class])
    override fun bulkPersistFromClient(
        items: List<GoalProgressSyncDto>
    ): ZikrPointBulkSyncResponseDto {

        log.info(
            "[REPO] 📥 🚀 Bulk goal progress sync → START | itemsCount={}",
            items.size
        )

        if (items.isEmpty()) {
            log.warn(
                "[REPO] ⚠️ ⏭️ Bulk goal progress sync → SKIPPED | reason=EmptyItemList"
            )
            return ZikrPointBulkSyncResponseDto(
                summary = SyncSummaryDto(syncedAt = Instant.now()),
                acknowledge = emptyList()
            )
        }

        val start = System.currentTimeMillis()
        val now = Instant.now()

        val existingMap =
            goalProgressJpaRepository.findAllById(items.map { it.id.toUUID() })
                .associateBy { it.id }

        val toSave = mutableListOf<GoalProgressEntity>()
        val acknowledge = mutableListOf<SyncAcknowledgeDto>()

        var created = 0
        var updated = 0
        var failed = 0

        items.forEach { dto ->
            try {
                val entity = dto.toEntity(existingMap[dto.id.toUUID()])
                toSave += entity

                if (existingMap.containsKey(entity.id)) {
                    updated++
                    acknowledge += SyncAcknowledgeDto(dto.id, "UPDATED")
                } else {
                    created++
                    acknowledge += SyncAcknowledgeDto(dto.id, "CREATED")
                }
            } catch (e: Exception) {
                failed++
                log.error(
                    "[REPO] 🔴 ❌ Bulk goal progress sync item → FAILED | id={} | reason={}",
                    dto.id,
                    e.message,
                    e
                )
                acknowledge += SyncAcknowledgeDto(dto.id, "FAILED", e.message)
            }
        }

        goalProgressJpaRepository.saveAll(toSave)
        syncLogRepository.updateSyncLog("goal_progress")

        log.info(
            "[REPO] ✅ 🔹 Bulk goal progress sync → SUCCESS | created={} | updated={} | failed={} | timeMs={}",
            created,
            updated,
            failed,
            System.currentTimeMillis() - start
        )

        return ZikrPointBulkSyncResponseDto(
            summary = SyncSummaryDto(
                syncedAt = now,
                created = created,
                updated = updated,
                failed = failed
            ),
            acknowledge = acknowledge
        )
    }

    @Transactional(readOnly = true)
    override fun getUncompletedRecords(): List<GoalProgressModel> {
        log.info("[REPO] 🔹 🚀 Fetch uncompleted goal progress → START")

        val result = goalProgressJpaRepository.findUncompleted().map { it.toModel() }

        log.info(
            "[REPO] ✅ 🔹 Fetch uncompleted goal progress → SUCCESS | count={}",
            result.size
        )
        return result
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun incrementGoalProgress(id: String, level: Int): Boolean {
        log.info(
            "[REPO] 🔹 🚀 Increment goal progress → START | id={} | level={}",
            id,
            level
        )

        return try {
            val updated =
                goalProgressJpaRepository.incrementProgress(id.toUUID(), level, Instant.now()) > 0

            if (updated) {
                log.info(
                    "[REPO] ✅ 🔹 Increment goal progress → SUCCESS | id={} | level={}",
                    id,
                    level
                )
            } else {
                log.warn(
                    "[REPO] ⚠️ ⏭️ Increment goal progress → SKIPPED | reason=NotFound | id={}",
                    id
                )
            }
            updated
        } catch (e: Exception) {
            log.error(
                "[REPO] 🔴 ❌ Increment goal progress → FAILED | id={} | reason={}",
                id,
                e.message,
                e
            )
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun markGoalProgressAsComplete(id: String): Boolean {
        log.info(
            "[REPO] 🔹 🚀 Mark goal progress complete → START | id={}",
            id
        )

        return try {
            val updated =
                goalProgressJpaRepository.markAsComplete(id.toUUID(), Instant.now()) > 0

            if (updated) {
                log.info(
                    "[REPO] ✅ 🔹 Mark goal progress complete → SUCCESS | id={}",
                    id
                )
            } else {
                log.warn(
                    "[REPO] ⚠️ ⏭️ Mark goal progress complete → SKIPPED | reason=NotFound | id={}",
                    id
                )
            }
            updated
        } catch (e: Exception) {
            log.error(
                "[REPO] 🔴 ❌ Mark goal progress complete → FAILED | id={} | reason={}",
                id,
                e.message,
                e
            )
            false
        }
    }

    override fun countTotalUsers(): Long {
        log.info("[REPO] 🔹 🚀 Count distinct users → START")

        val totalUsers = goalProgressJpaRepository.countDistinctActiveUsers()

        log.info(
            "[REPO] ✅ 🔹 Count distinct users → SUCCESS | totalUsers={}",
            totalUsers
        )
        return totalUsers
    }


}