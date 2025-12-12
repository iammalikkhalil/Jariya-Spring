package com.example.demo.presentation.service.progress

import com.example.demo.domain.enums.PointsSourceType
import com.example.demo.domain.model.progress.toDto
import com.example.demo.domain.repository.auth.UserRepository
import com.example.demo.domain.repository.progress.ZikrPointRepository
import com.example.demo.domain.repository.progress.ZikrProgressRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.progress.ZikrPointDto
import com.example.demo.presentation.dto.progress.ZikrProgressDto
import com.example.demo.presentation.dto.progress.ZikrProgressRequestDto
import com.example.demo.presentation.dto.progress.toDomain
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrProgressService(
    private val zikrProgressRepo: ZikrProgressRepository,
    private val zikrPointRepo: ZikrPointRepository,
    private val userRepo: UserRepository
) {

    // -------------------- CRUD Bridge --------------------

    fun createZikrProgress(body: ZikrProgressRequestDto): Boolean {
        val dto = ZikrProgressDto(
            id = body.id,
            zikrId = body.zikrId,
            userId = body.userId,
            count = body.count,
            isCompleted = body.isCompleted,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt,
            deviceId = body.deviceId,
            sessionId = body.sessionId,
            source = body.source,
            processedLevels = body.processedLevels,
            isStarted = body.isStarted,
            syncedAt = body.syncedAt,
        )
        return zikrProgressRepo.createZikrProgress(dto.toDomain())
    }

    fun updateZikrProgress(body: ZikrProgressRequestDto): Boolean {
        val dto = ZikrProgressDto(
            id = body.id,
            zikrId = body.zikrId,
            userId = body.userId,
            count = body.count,
            isCompleted = body.isCompleted,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt,
            deviceId = body.deviceId,
            sessionId = body.sessionId,
            source = body.source,
            processedLevels = body.processedLevels,
            isStarted = body.isStarted,
            syncedAt = body.syncedAt,
        )
        return zikrProgressRepo.updateZikrProgress(dto.toDomain())
    }

    // -------------------- Async Handler --------------------

    @Async
    fun processUncompletedAsync() {
        runBlocking {
            try {
                val processed = handleZikrProgresses()
                Log.info("✅ Background progress handling completed. Total processed: ${processed.size}")
            } catch (e: Exception) {
                Log.error("❌ Failed in background progress handling: ${e.message}", e)
            }
        }
    }

    suspend fun handleZikrProgresses(): List<ZikrProgressDto> {
        val allProcessed = mutableListOf<ZikrProgressDto>()
        Log.info("Fetching unprocessed zikr progress records...")

        val progresses = zikrProgressRepo.getUncompletedRecords()
        if (progresses.isEmpty()) {
            Log.info("✅ No pending zikr progress found.")
            return allProcessed
        }

        Log.info("Found ${progresses.size} unprocessed record(s)")
        for (progress in progresses) {
            try {
                Log.info("🧠 Processing progressId=${progress.id} for userId=${progress.userId}")
                val updated = processSingleZikrProgress(progress.toDto())
                if (updated != null) allProcessed.add(updated)
            } catch (e: Exception) {
                Log.error("❌ Error while processing progressId=${progress.id}", e)
                continue
            }
        }
        return allProcessed
    }

    private suspend fun processSingleZikrProgress(progress: ZikrProgressDto): ZikrProgressDto? {
        if (!progress.isStarted) {
            Log.info("🟢 Starting first-time processing for progressId=${progress.id}")

            val alreadyExists = zikrPointRepo.pointExists(progress.id, progress.userId, 0)
            if (!alreadyExists) {
                val basePoint = progress.count * 100
                val basePointDto = ZikrPointDto(
                    id = generateUUID(),
                    zikrId = progress.zikrId,
                    userId = progress.userId,
                    points = basePoint,
                    pointsSourceType = PointsSourceType.ZIKR,
                    sourceUser = progress.userId,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now(),
                    isDeleted = false,
                    deletedAt = null,
                    progressId = progress.id,
                    level = 0,
                )
                val inserted = zikrPointRepo.createZikrPoint(basePointDto.toDomain())
                if (!inserted) throw IllegalStateException("Base point insert failed")
            }
            zikrProgressRepo.incrementZikrProgress(progress.id, 0)
        }
        return processReferralLevels(progress)
    }

    private suspend fun processReferralLevels(progress: ZikrProgressDto): ZikrProgressDto? {
        var currentUserId = progress.userId
        val sourceUserId = progress.userId
        val basePoint = progress.count * 100
        val progressId = progress.id
        val zikrId = progress.zikrId ?: return null
        var currentLevel = (progress.processedLevels ?: 0) + 1

        while (true) {
            val currentUser = userRepo.findUserById(currentUserId)
                ?: break

            val parentId = currentUser.referredBy?.takeIf { it.isNotBlank() } ?: break
            val exists = zikrPointRepo.pointExists(progressId, parentId, currentLevel)
            if (exists) {
                currentUserId = parentId
                currentLevel++
                continue
            }

            val referralPoint = ZikrPointDto(
                id = generateUUID(),
                zikrId = zikrId,
                userId = parentId,
                points = basePoint,
                pointsSourceType = PointsSourceType.REFERRAL,
                sourceUser = sourceUserId,
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
                isDeleted = false,
                deletedAt = null,
                progressId = progressId,
                level = currentLevel
            )
            val inserted = zikrPointRepo.createZikrPoint(referralPoint.toDomain())
            if (!inserted) throw IllegalStateException("Referral point insert failed")

            zikrProgressRepo.incrementZikrProgress(progressId, 0)
            currentUserId = parentId
            currentLevel++
        }

        zikrProgressRepo.markZikrProgressAsComplete(progressId)
        return zikrProgressRepo.getZikrProgressById(progressId)?.toDto()
    }
}