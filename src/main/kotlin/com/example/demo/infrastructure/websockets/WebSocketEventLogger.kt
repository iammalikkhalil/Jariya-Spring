package com.example.demo.infrastructure.websockets

import com.example.demo.domain.enums.websockets.StatsTopic
import com.example.demo.presentation.service.websockets.StatsBroadcastService
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.*

@Component
class WebSocketEventLogger(
    private val statsBroadcastService: StatsBroadcastService
) {

    private val log = LoggerFactory.getLogger(WebSocketEventLogger::class.java)

    @EventListener
    fun onConnect(event: SessionConnectEvent) {
        val sessionId = StompHeaderAccessor.wrap(event.message).sessionId ?: return

        WebSocketStatsTracker.onSessionConnected(sessionId)
        statsBroadcastService.broadcastGlobalStats()

        log.info("🟢 CONNECT | session={} | activeSessions={}",
            sessionId,
            WebSocketStatsTracker.getTotalActiveSessions()
        )
    }

    @EventListener
    fun onDisconnect(event: SessionDisconnectEvent) {
        val sessionId = StompHeaderAccessor.wrap(event.message).sessionId ?: return

        WebSocketStatsTracker.onSessionDisconnected(sessionId)
        statsBroadcastService.broadcastGlobalStats()

        log.info("🔴 DISCONNECT | session={} | activeSessions={}",
            sessionId,
            WebSocketStatsTracker.getTotalActiveSessions()
        )
    }

    @EventListener
    fun onSubscribe(event: SessionSubscribeEvent) {
        val accessor = StompHeaderAccessor.wrap(event.message)
        val sessionId = accessor.sessionId ?: return
        val destination = accessor.destination ?: return

        val topic = StatsTopic.fromDestination(destination) ?: return

        WebSocketStatsTracker.onTopicSubscribed(sessionId, topic)

        when (topic) {
            StatsTopic.GLOBAL -> statsBroadcastService.broadcastGlobalStats()
            StatsTopic.GOALS -> statsBroadcastService.broadcastGoalsStats()
            else -> {}
        }

        log.info("📡 SUBSCRIBE | session={} | topic={} | count={}",
            sessionId,
            topic.name,
            WebSocketStatsTracker.getActiveUsersForTopic(topic)
        )
    }

    @EventListener
    fun onUnsubscribe(event: SessionUnsubscribeEvent) {
        val accessor = StompHeaderAccessor.wrap(event.message)
        val sessionId = accessor.sessionId ?: return
        val destination = accessor.destination ?: return

        val topic = StatsTopic.fromDestination(destination) ?: return

        WebSocketStatsTracker.onTopicUnsubscribed(sessionId, topic)

        log.info("📴 UNSUBSCRIBE | session={} | topic={} | count={}",
            sessionId,
            topic.name,
            WebSocketStatsTracker.getActiveUsersForTopic(topic)
        )
    }
}
