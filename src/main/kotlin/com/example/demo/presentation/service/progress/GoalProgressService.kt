package com.example.demo.presentation.service.progress

import com.example.demo.domain.enums.PointsSourceType
import com.example.demo.domain.model.progress.toDto
import com.example.demo.domain.repository.auth.UserRepository
import com.example.demo.domain.repository.progress.GoalProgressRepository
import com.example.demo.domain.repository.progress.ZikrPointRepository
import com.example.demo.domain.repository.progress.ZikrProgressRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.progress.ZikrPointDto
import com.example.demo.presentation.dto.progress.GoalProgressDto
import com.example.demo.presentation.dto.progress.GoalProgressRequestDto
import com.example.demo.presentation.dto.progress.toDomain
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class GoalProgressService(
    private val goalProgressRepository: GoalProgressRepository,
) {

    // -------------------- CRUD Bridge --------------------

    fun createGoalProgress(body: GoalProgressRequestDto): Boolean {
        val dto = GoalProgressDto(
            id = body.id,
            zikrId = body.zikrId,
            userId = body.userId,
            goalId = body.goalId,
            count = body.count,
            charCount = body.charCount,
            isCompleted = body.isCompleted,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt,
            deviceId = body.deviceId,
            sessionId = body.sessionId,
            type = body.type,
            processedLevels = body.processedLevels,
            isStarted = body.isStarted,
            syncedAt = body.syncedAt,
        )
        return goalProgressRepository.createGoalProgress(dto.toDomain())
    }
}