package com.example.demo.data.mapper.progress

import com.example.demo.data.entity.QuranProgressEntity
import com.example.demo.data.entity.UserEntity
import com.example.demo.data.entity.ZikrEntity
import com.example.demo.data.entity.ZikrProgressEntity
import com.example.demo.domain.model.progress.QuranProgressModel
import com.example.demo.domain.model.progress.ZikrProgressModel
import com.example.demo.presentation.dto.sync.QuranProgressSyncDto
import com.example.demo.presentation.dto.sync.ZikrProgressSyncDto
import java.lang.Character.charCount
import java.util.*

fun QuranProgressEntity.toModel(): QuranProgressModel =
    QuranProgressModel(
        id = id.toString(),
        userId = user ?: "",
        ayahId = ayahId,
        surahId = surahId,
        deviceId = deviceId?.toString(),
        sessionId = sessionId?.toString(),
        source = source,
        count = count,
        charCount = charCount?: 1,
        processedLevels = processedLevels,
        isStarted = isStarted,
        isCompleted = isCompleted,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        syncedAt = syncedAt
    )

fun QuranProgressModel.toEntity(): QuranProgressEntity =
    QuranProgressEntity(
        id = UUID.fromString(id),
        user = userId,
        ayahId = ayahId,
        surahId = surahId,
        deviceId = deviceId?.let { UUID.fromString(it) },
        sessionId = sessionId?.let { UUID.fromString(it) },
        source = source,
        count = count,
        charCount = charCount,
        processedLevels = processedLevels,
        isStarted = isStarted,
        isCompleted = isCompleted,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        syncedAt = syncedAt
    )



fun QuranProgressSyncDto.toEntity(): QuranProgressEntity =
    QuranProgressEntity(
        id = UUID.fromString(id),
        user = userId,
        surahId = surahId?.toInt(),
        deviceId = deviceId?.let { UUID.fromString(it) },
        sessionId = sessionId?.let { UUID.fromString(it) },
        source = source,
        ayahId = ayahId.toIntOrNull() ?: 0,
        count = count.toIntOrNull() ?: 0,
        charCount = charCount?.toIntOrNull() ?: 0,
        processedLevels = processedLevels,
        isStarted = isStarted,
        isCompleted = isCompleted,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        syncedAt = syncedAt
    )