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
        WebSocketStatsTracker.onSessionConnected()
        statsBroadcastService.broadcastGlobalStats()
        log.info("🟢 WS CONNECT | activeSessions={}", WebSocketStatsTracker.getTotalActiveSessions())
    }

    @EventListener
    fun onDisconnect(event: SessionDisconnectEvent) {
        WebSocketStatsTracker.onSessionDisconnected()
        statsBroadcastService.broadcastGlobalStats()
        log.info("🔴 WS DISCONNECT | activeSessions={}", WebSocketStatsTracker.getTotalActiveSessions())
    }

    @EventListener
    fun onSubscribe(event: SessionSubscribeEvent) {
        val destination = StompHeaderAccessor.wrap(event.message).destination
        val topic = StatsTopic.fromDestination(destination) ?: return

        WebSocketStatsTracker.onTopicSubscribed(topic)

        // ✅ SEND DATA IMMEDIATELY
        when (topic) {
            StatsTopic.GLOBAL -> statsBroadcastService.broadcastGlobalStats()
            StatsTopic.GOALS -> statsBroadcastService.broadcastGoalsStats()
            else -> {}
        }

        log.info(
            "📡 SUBSCRIBE | topic={} | count={}",
            topic.name,
            WebSocketStatsTracker.getActiveUsersForTopic(topic)
        )
    }

    @EventListener
    fun onUnsubscribe(event: SessionUnsubscribeEvent) {
        val destination = StompHeaderAccessor.wrap(event.message).destination
        val topic = StatsTopic.fromDestination(destination) ?: return

        WebSocketStatsTracker.onTopicUnsubscribed(topic)
        // ✅ SEND DATA IMMEDIATELY
        when (topic) {
            StatsTopic.GOALS -> statsBroadcastService.broadcastGoalsStats()
            else -> {}
        }


        log.info(
            "📴 UNSUBSCRIBE | topic={} | count={}",
            topic.name,
            WebSocketStatsTracker.getActiveUsersForTopic(topic)
        )
    }
}
