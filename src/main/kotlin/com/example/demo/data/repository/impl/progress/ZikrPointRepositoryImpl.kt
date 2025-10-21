package com.example.demo.data.repository.impl.progress

import LeaderboardModel
import com.example.demo.data.mapper.auth.toModel
import com.example.demo.data.mapper.progress.toEntity
import com.example.demo.data.mapper.progress.toModel
import com.example.demo.data.repository.jpa.auth.UserJpaRepository
import com.example.demo.data.repository.jpa.progress.ZikrPointJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.data.repository.jpa.progress.ZikrProgressJpaRepository
import com.example.demo.domain.model.progress.ZikrPointModel
import com.example.demo.domain.model.progress.ZikrPointSummaryModel
import com.example.demo.domain.repository.progress.ZikrPointRepository
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class ZikrPointRepositoryImpl(
    private val zikrPointJpaRepository: ZikrPointJpaRepository,
    private val userJpaRepository: UserJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val zikrProgressJpaRepository: ZikrProgressJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrPointRepository {

    override fun getAllZikrPoints(): List<ZikrPointModel> =
        zikrPointJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrPointById(id: String): ZikrPointModel? =
        zikrPointJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun createZikrPoint(zikrPoint: ZikrPointModel): Boolean {
        return try {
            val userEntity = userJpaRepository.findById(zikrPoint.userId.toUUID()).orElse(null) ?: return false
            val sourceUserEntity = userJpaRepository.findById(zikrPoint.sourceUser.toUUID()).orElse(null) ?: return false
            val zikrEntity = zikrPoint.zikrId?.let { zikrJpaRepository.findById(it.toUUID()).orElse(null) }
            val progressEntity = zikrPoint.progressId?.let { zikrProgressJpaRepository.findById(it.toUUID()).orElse(null) }

            val entity = zikrPoint.toEntity(
                userEntity = userEntity,
                sourceUserEntity = sourceUserEntity,
                zikrEntity = zikrEntity,
                progressEntity = progressEntity
            )

            zikrPointJpaRepository.save(entity)
            syncLogRepository.updateSyncLog("zikr_point")
            Log.info("✅ Created ZikrPoint: ${zikrPoint.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrPoint: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun updateZikrPoint(zikrPoint: ZikrPointModel): Boolean {
        return try {
            if (!zikrPointJpaRepository.existsById(zikrPoint.id.toUUID())) return false

            val userEntity = userJpaRepository.findById(zikrPoint.userId.toUUID()).orElse(null) ?: return false
            val sourceUserEntity = userJpaRepository.findById(zikrPoint.sourceUser.toUUID()).orElse(null) ?: return false
            val zikrEntity = zikrPoint.zikrId?.let { zikrJpaRepository.findById(it.toUUID()).orElse(null) }
            val progressEntity = zikrPoint.progressId?.let { zikrProgressJpaRepository.findById(it.toUUID()).orElse(null) }

            val entity = zikrPoint.toEntity(
                userEntity = userEntity,
                sourceUserEntity = sourceUserEntity,
                zikrEntity = zikrEntity,
                progressEntity = progressEntity
            )

            zikrPointJpaRepository.save(entity)
            syncLogRepository.updateSyncLog("zikr_point")
            Log.info("✅ Updated ZikrPoint: ${zikrPoint.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrPoint: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun deleteZikrPoint(id: String): Boolean {
        return try {
            val deleted = zikrPointJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_point")
                Log.info("✅ Soft-deleted ZikrPoint: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrPoint: ${e.message}", e)
            false
        }
    }

    override fun pointExists(progressId: String, userId: String, level: Int): Boolean =
        zikrPointJpaRepository.existsByProgressAndUserAndLevel(progressId.toUUID(), userId.toUUID(), level)

    override fun getZikrPointsSummary(userId: String): ZikrPointSummaryModel {
        val referralPoints = zikrPointJpaRepository.getReferralPoints(userId.toUUID()) ?: 0
        val zikrPoints = zikrPointJpaRepository.getZikrPoints(userId.toUUID()) ?: 0
        val totalZikrPoints = zikrPointJpaRepository.getTotalZikrPoints() ?: 0
        return ZikrPointSummaryModel(referralPoints, zikrPoints, totalZikrPoints)
    }

    override fun getZikrPointsTotal(): ZikrPointSummaryModel {
        val totalZikrPoints = zikrPointJpaRepository.getTotalZikrPoints() ?: 0
        return ZikrPointSummaryModel(0, 0, totalZikrPoints)
    }

    override fun getLeaderboard(): LeaderboardModel {
        val allPoints = zikrPointJpaRepository.findAllByIsDeletedFalse()
        val total = allPoints.sumOf { it.points }
        val topTen = allPoints.groupBy { it.user }
            .mapValues { entry -> entry.value.sumOf { it.points } }
            .entries.sortedByDescending { it.value }
            .take(10)
            .mapIndexed { i, entry ->
                LeaderboardModel.UserRank(i + 1, entry.key.toModel(), entry.value)
            }
        return LeaderboardModel(total, topTen)
    }

    override fun getZikrLeaderboard(): LeaderboardModel {
        val zikrPoints = zikrPointJpaRepository.findAllByIsDeletedFalse()
            .filter { it.sourceType == "ZIKR" }
        val total = zikrPoints.sumOf { it.points }
        val topTen = zikrPoints.groupBy { it.user }
            .mapValues { entry -> entry.value.sumOf { it.points } }
            .entries.sortedByDescending { it.value }
            .take(10)
            .mapIndexed { i, entry ->
                LeaderboardModel.UserRank(i + 1, entry.key.toModel(), entry.value)
            }
        return LeaderboardModel(total, topTen)
    }
}