package com.example.demo.data.repository.impl.progress

import LeaderboardModel
import com.example.demo.data.mapper.auth.toModel
import com.example.demo.data.mapper.progress.toEntity
import com.example.demo.data.mapper.progress.toModel
import com.example.demo.data.repository.jpa.auth.UserJpaRepository
import com.example.demo.data.repository.jpa.progress.ZikrPointJpaRepository
import com.example.demo.data.repository.jpa.progress.ZikrProgressJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
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

    // ✅ Eager load: JOIN FETCH inside repo (super fast, no lazy loading)
    @Transactional(readOnly = true)
    override fun getAllZikrPoints(): List<ZikrPointModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching all ZikrPoints with JOIN FETCH...")

        val result = zikrPointJpaRepository.findAllActive().map { it.toModel() }

        Log.info("✅ getAllZikrPoints completed in ${System.currentTimeMillis() - start}ms (${result.size} records)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getZikrPointById(id: String): ZikrPointModel? =
        zikrPointJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikrPoint(zikrPoint: ZikrPointModel): Boolean = try {
        val user = userJpaRepository.getReferenceById(zikrPoint.userId.toUUID())
        val sourceUser = userJpaRepository.getReferenceById(zikrPoint.sourceUser.toUUID())
        val zikr = zikrPoint.zikrId?.let { zikrJpaRepository.getReferenceById(it.toUUID()) }
        val progress = zikrPoint.progressId?.let { zikrProgressJpaRepository.getReferenceById(it.toUUID()) }

        val entity = zikrPoint.toEntity(user, sourceUser, zikr, progress)
        zikrPointJpaRepository.save(entity)
        syncLogRepository.updateSyncLog("zikr_point")

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

            val user = userJpaRepository.getReferenceById(zikrPoint.userId.toUUID())
            val sourceUser = userJpaRepository.getReferenceById(zikrPoint.sourceUser.toUUID())
            val zikr = zikrPoint.zikrId?.let { zikrJpaRepository.getReferenceById(it.toUUID()) }
            val progress = zikrPoint.progressId?.let { zikrProgressJpaRepository.getReferenceById(it.toUUID()) }

            val entity = zikrPoint.toEntity(user, sourceUser, zikr, progress)
            zikrPointJpaRepository.save(entity)
            syncLogRepository.updateSyncLog("zikr_point")

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
            Log.info("🗑 Soft deleted ZikrPoint: $id")
            true
        } else false
    } catch (e: Exception) {
        Log.error("❌ Error deleting ZikrPoint: ${e.message}", e)
        false
    }

    @Transactional(readOnly = true)
    override fun pointExists(progressId: String, userId: String, level: Int): Boolean =
        zikrPointJpaRepository.existsByProgressAndUserAndLevel(progressId.toUUID(), userId.toUUID(), level)

    @Transactional(readOnly = true)
    override fun getZikrPointsSummary(userId: String): ZikrPointSummaryModel {
        val referralPoints = zikrPointJpaRepository.getReferralPoints(userId.toUUID()) ?: 0
        val zikrPoints = zikrPointJpaRepository.getZikrPoints(userId.toUUID()) ?: 0
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
        val topTen = allPoints.groupBy { it.user }
            .mapValues { it.value.sumOf { zp -> zp.points } }
            .entries.sortedByDescending { it.value }
            .take(10)
            .mapIndexed { i, entry ->
                LeaderboardModel.UserRank(i + 1, entry.key.toModel(), entry.value)
            }
        return LeaderboardModel(total, topTen)
    }

    @Transactional(readOnly = true)
    override fun getZikrLeaderboard(): LeaderboardModel {
        val zikrPoints = zikrPointJpaRepository.findAllActive()
            .filter { it.sourceType == "ZIKR" }
        val total = zikrPoints.sumOf { it.points }
        val topTen = zikrPoints.groupBy { it.user }
            .mapValues { it.value.sumOf { zp -> zp.points } }
            .entries.sortedByDescending { it.value }
            .take(10)
            .mapIndexed { i, entry ->
                LeaderboardModel.UserRank(i + 1, entry.key.toModel(), entry.value)
            }
        return LeaderboardModel(total, topTen)
    }
}