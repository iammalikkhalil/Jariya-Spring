package com.example.demo.presentation.service.websockets


import com.example.demo.domain.enums.websockets.StatsTopic
import com.example.demo.infrastructure.websockets.WebSocketStatsTracker
import com.example.demo.presentation.dto.websockets.GlobalStatsDto
import com.example.demo.presentation.dto.websockets.GoalsStatsPayloadDto
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class StatsBroadcastService(
    private val messagingTemplate: SimpMessagingTemplate,
    private val cache: StatsCacheService
) {

    /* ===================== GLOBAL ===================== */

    fun broadcastGlobalStats() {
        val payload = GlobalStatsDto(
            totalUsers = cache.getTotalUsers(),
            activeUsers = WebSocketStatsTracker.getTotalActiveSessions(),
            totalHasanats = cache.getTotalHasanats()
        )

        send(StatsTopic.GLOBAL, payload)
    }

    /* ===================== GOALS ===================== */

    fun broadcastGoalsStats() {
        val payload = GoalsStatsPayloadDto(
            goals = cache.getGoalsStats(),
            totalUsers = cache.getTotalGoalsUsers(),
            activeUsers = WebSocketStatsTracker.getActiveUsersForTopic(StatsTopic.GOALS)
        )

        send(StatsTopic.GOALS, payload)
    }

    /* ===================== INTERNAL ===================== */

    private fun send(topic: StatsTopic, payload: Any) {
        messagingTemplate.convertAndSend(topic.destination, payload)
    }
}
