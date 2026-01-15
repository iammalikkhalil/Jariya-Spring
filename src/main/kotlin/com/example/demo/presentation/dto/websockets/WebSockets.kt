package com.example.demo.presentation.dto.websockets

import java.time.Instant

data class DummySocketMessage(
    val message: String,
    val timestamp: Instant? = null,
)

data class GlobalStatsDto(
    val totalUsers: Long,
    val activeUsers: Long,
    val totalHasanats: Long
)

data class GoalStatsDto(
    val goalId: String,
    val earnedHasanats: Long
)

data class GoalStatsModel(
    val goalId: String,
    val totalUsers: Long,
    val totalHasanats: Long
)

data class GoalsStatsPayloadDto(
    val goals: List<GoalStatsDto>,
    val totalUsers: Long,
    val activeUsers: Long,
)

data class CollectionStatsDto(
    val collectionId: String,
    val totalUsers: Long,
    val activeUsers: Long,
    val totalHasanats: Long
)

data class CollectionsStatsPayloadDto(
    val collections: List<CollectionStatsDto>
)

data class ZikrStatsDto(
    val zikrId: String,
    val totalUsers: Long,
    val activeUsers: Long,
    val totalHasanats: Long
)

data class ZikrStatsPayloadDto(
    val zikrs: List<ZikrStatsDto>
)