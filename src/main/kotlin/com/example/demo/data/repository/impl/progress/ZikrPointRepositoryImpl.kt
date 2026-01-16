package com.example.demo.data.repository.impl.progress

import com.example.demo.domain.model.progress.LeaderboardModel
import com.example.demo.data.entity.ZikrPointEntity
import com.example.demo.data.mapper.auth.toModel
import com.example.demo.data.mapper.progress.toEntity
import com.example.demo.data.mapper.progress.toModel
import com.example.demo.data.repository.jpa.auth.UserJpaRepository
import com.example.demo.data.repository.jpa.progress.ZikrPointJpaRepository
import com.example.demo.data.repository.jpa.progress.ZikrProgressJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.domain.events.GoalsStatsChangedEvent
import com.example.demo.domain.model.progress.ZikrPointModel
import com.example.demo.domain.model.progress.ZikrPointSummaryModel
import com.example.demo.domain.repository.progress.ZikrPointRepository
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import com.example.demo.presentation.dto.sync.SyncAcknowledgeDto
import com.example.demo.presentation.dto.sync.SyncSummaryDto
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import com.example.demo.presentation.dto.sync.ZikrPointSyncDto
import com.example.demo.presentation.dto.websockets.GoalStatsDto
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class ZikrPointRepositoryImpl(
    private val zikrPointJpaRepository: ZikrPointJpaRepository,
    private val userJpaRepository: UserJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val zikrProgressJpaRepository: ZikrProgressJpaRepository,
    private val syncLogRepository: SyncLogRepository,
    private val eventPublisher: ApplicationEventPublisher
) : ZikrPointRepository {

    /* ===================== READ ===================== */

    @Transactional(readOnly = true)
    override fun getAllZikrPoints(): List<ZikrPointModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching all ZikrPoints with JOIN FETCH...")

        val result = zikrPointJpaRepository.findAllActive().map { it.toModel() }

        Log.info(
            "✅ getAllZikrPoints completed in ${System.currentTimeMillis() - start}ms (${result.size} records)"
        )
        return result
    }

    @Transactional(readOnly = true)
    override fun getZikrPointById(id: String): ZikrPointModel? =
        zikrPointJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional(readOnly = true)
    override fun pointExists(progressId: String, userId: String, level: Int): Boolean =
        zikrPointJpaRepository.existsByProgressAndUserAndLevel(
            progressId,
            userId,
            level
        )

    @Transactional(readOnly = true)
    override fun getZikrPointsSummary(userId: String): ZikrPointSummaryModel {
        val referralPoints = zikrPointJpaRepository.getReferralPoints(userId) ?: 0
        val zikrPoints = zikrPointJpaRepository.getZikrPoints(userId) ?: 0
        val totalZikrPoints = zikrPointJpaRepository.getTotalZikrPoints() ?: 0
        return ZikrPointSummaryModel(referralPoints, zikrPoints, totalZikrPoints)
    }

    @Transactional(readOnly = true)
    override fun getZikrPointsTotal(): ZikrPointSummaryModel {
        val totalZikrPoints = zikrPointJpaRepository.getTotalZikrPoints() ?: 0
        return ZikrPointSummaryModel(0, 0, totalZikrPoints)
    }

    @Transactional(readOnly = true)
    override fun getLeaderboard(): LeaderboardModel {

        val allPoints = zikrPointJpaRepository.findAllActive()

        val total = allPoints.sumOf { it.points }

        val topTen = allPoints
            .groupBy { it.user } // ✅ userId (String)
            .mapValues { it.value.sumOf { zp -> zp.points } }
            .entries
            .sortedByDescending { it.value }
            .take(10)
            .mapIndexedNotNull { i, entry ->

                val user = userJpaRepository.findById(entry.key.toUUID()).orElse(null)
                    ?: return@mapIndexedNotNull null // ✅ skip if user not found

                LeaderboardModel.UserRank(i + 1, user.toModel(), entry.value)
            }

        return LeaderboardModel(total, topTen)
    }

    @Transactional(readOnly = true)
    override fun getZikrLeaderboard(): LeaderboardModel {

        val zikrPoints = zikrPointJpaRepository.findAllActive()
            .filter { it.sourceType == "ZIKR" }

        val total = zikrPoints.sumOf { it.points }

        val topTen = zikrPoints
            .groupBy { it.user } // ✅ userId string
            .mapValues { it.value.sumOf { zp -> zp.points } }
            .entries
            .sortedByDescending { it.value }
            .take(10)
            .mapIndexedNotNull { i, entry ->

                val user = userJpaRepository.findById(entry.key.toUUID()).orElse(null)
                    ?: return@mapIndexedNotNull null  // ✅ skip if user not found

                LeaderboardModel.UserRank(i + 1, user.toModel(), entry.value)
            }

        return LeaderboardModel(total, topTen)
    }


    @Transactional(readOnly = true)
    override fun getStatsByGoalId(): List<GoalStatsDto> {

        val result = zikrPointJpaRepository.getGoalsStatsGrouped()

        val mappedResult = result.map {
            GoalStatsDto(
                goalId = it.getGoalId(),
                earnedHasanats = it.getEarnedHasanats()
            )
        }
        return mappedResult
    }



    @Transactional(readOnly = true)
    override fun countTotalUsers(): Long {
        val totalUsers = zikrPointJpaRepository.countDistinctActiveUsers()
        Log.info("👥 [GOALS] Total distinct users (non-null) = $totalUsers")
        return totalUsers
    }

    /* ===================== WRITE ===================== */

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikrPoint(zikrPoint: ZikrPointModel): Boolean = try {

        val zikr = zikrPoint.zikrId?.let {
            zikrJpaRepository.findById(it.toUUID())
                .orElseThrow { IllegalArgumentException("Zikr not found: $it") }
        }

        zikrPoint.progressId.let {
            zikrProgressJpaRepository.findById(it.toUUID())
                .orElseThrow { IllegalArgumentException("Progress not found: $it") }
        }

        val entity = zikrPoint.toEntity(zikr)
        zikrPointJpaRepository.save(entity)

        syncLogRepository.updateSyncLog("zikr_point")
        eventPublisher.publishEvent(GoalsStatsChangedEvent())

        Log.info("✅ Created ZikrPoint: ${zikrPoint.id}")
        true

    } catch (e: Exception) {
        Log.error("❌ Error creating ZikrPoint: ${e.message}", e)
        false
    }


    @Transactional(rollbackFor = [Exception::class])
    override fun updateZikrPoint(zikrPoint: ZikrPointModel): Boolean {
        return try {
            if (!zikrPointJpaRepository.existsById(zikrPoint.id.toUUID())) return false

            val zikr = zikrPoint.zikrId?.let { zikrJpaRepository.getReferenceById(it.toUUID()) }
            zikrPoint.progressId.let { zikrProgressJpaRepository.getReferenceById(it.toUUID()) }

            val entity = zikrPoint.toEntity(zikr)
            zikrPointJpaRepository.save(entity)
            syncLogRepository.updateSyncLog("zikr_point")

            // ✅ EVENTS (only after successful write)
            
            eventPublisher.publishEvent(GoalsStatsChangedEvent())

            Log.info("✅ Updated ZikrPoint: ${zikrPoint.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrPoint: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteZikrPoint(id: String): Boolean = try {
        val deleted = zikrPointJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
        if (deleted > 0) {
            syncLogRepository.updateSyncLog("zikr_point")

            // ✅ EVENTS (only when something actually deleted)
            eventPublisher.publishEvent(GoalsStatsChangedEvent())

            Log.info("🗑 Soft deleted ZikrPoint: $id")
            true
        } else false
    } catch (e: Exception) {
        Log.error("❌ Error deleting ZikrPoint: ${e.message}", e)
        false
    }



    @Transactional(rollbackFor = [Exception::class])
    override fun bulkPersistFromClient(
        items: List<ZikrPointSyncDto>
    ): ZikrPointBulkSyncResponseDto {

        val start = System.currentTimeMillis()
        Log.info("🚀 Bulk ZikrPoint sync started (${items.size} items)")

        if (items.isEmpty()) {
            return ZikrPointBulkSyncResponseDto(
                summary = SyncSummaryDto(),
                acknowledge = emptyList()
            )
        }

        /* -----------------------------
           EXISTING ZIKR POINTS
           ----------------------------- */
        val ids = items.map { it.id.toUUID() }

        val existingMap =
            zikrPointJpaRepository.findAllById(ids)
                .associateBy { it.id }

        /* -----------------------------
           LOAD ZIKRS (optional relation)
           ----------------------------- */
        val zikrIds = items
            .map { it.zikrId }
            .distinct()
            .map { it.toUUID() }

        val zikrMap =
            if (zikrIds.isNotEmpty())
                zikrJpaRepository.findAllById(zikrIds).associateBy { it.id }
            else
                emptyMap()

        /* -----------------------------
           RESULT HOLDERS
           ----------------------------- */
        val toSave = mutableListOf<ZikrPointEntity>()
        val acknowledge = mutableListOf<SyncAcknowledgeDto>()

        var created = 0
        var updated = 0
        var failed = 0

        /* -----------------------------
           PROCESS EACH ITEM
           ----------------------------- */
        items.forEach { dto ->
            try {

                val zikr = dto.zikrId.let {
                    zikrMap[it.toUUID()] ?: throw IllegalStateException("Zikr not found: $it")
                }

                val entity = ZikrPointEntity(
                    id = dto.id.toUUID(),
                    user = dto.userId, // ✅ String
                    sourceUser = dto.sourceUserId, // ✅ can be null
                    zikr = zikr,
                    progressType = dto.progressType,
                    progressId = dto.progressId,
                    level = dto.level,
                    points = dto.points,
                    sourceType = dto.distributionType,
                    createdAt = Instant.ofEpochMilli(dto.createdAt),
                    updatedAt = dto.updatedAt?.let { Instant.ofEpochMilli(it) } ?: Instant.now(),
                    isDeleted = dto.isDeleted,
                    deletedAt = if (dto.isDeleted) Instant.now() else null
                )

                toSave += entity

                if (existingMap.containsKey(entity.id)) {
                    updated++
                    acknowledge += SyncAcknowledgeDto(
                        id = entity.id.toString(),
                        status = "UPDATED"
                    )
                } else {
                    created++
                    acknowledge += SyncAcknowledgeDto(
                        id = entity.id.toString(),
                        status = "CREATED"
                    )
                }

            } catch (e: Exception) {
                failed++
                Log.error("❌ Failed ZikrPoint sync for ${dto.id}", e)
                acknowledge += SyncAcknowledgeDto(
                    id = dto.id,
                    status = "FAILED",
                    message = e.message
                )
            }
        }

        /* -----------------------------
           BULK SAVE
           ----------------------------- */
        if (toSave.isNotEmpty()) {
            zikrPointJpaRepository.saveAll(toSave)
        }

        syncLogRepository.updateSyncLog("zikr_point")

        // ✅ Single event after successful bulk write
        eventPublisher.publishEvent(GoalsStatsChangedEvent())

        Log.info(
            "✅ Bulk ZikrPoint sync completed in ${System.currentTimeMillis() - start}ms " +
                    "(created=$created, updated=$updated, failed=$failed)"
        )

        return ZikrPointBulkSyncResponseDto(
            summary = SyncSummaryDto(
                syncedAt = Instant.now(),
                created = created,
                updated = updated,
                failed = failed
            ),
            acknowledge = acknowledge
        )
    }




}
