package com.example.demo.infrastructure.websockets

import com.example.demo.domain.enums.websockets.StatsTopic
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

object WebSocketStatsTracker {

    private val totalActiveSessions = AtomicInteger(0)

    private val topicCounters: MutableMap<StatsTopic, AtomicInteger> =
        ConcurrentHashMap()

    /* ---------------- Session Level ---------------- */

    fun onSessionConnected() {
        totalActiveSessions.incrementAndGet()
    }

    fun onSessionDisconnected() {
        totalActiveSessions.updateAndGet { count ->
            if (count > 0) count - 1 else 0
        }
    }

    fun getTotalActiveSessions(): Long =
        totalActiveSessions.get().toLong()

    /* ---------------- Topic Level ---------------- */

    fun onTopicSubscribed(topic: StatsTopic) {
        topicCounters
            .computeIfAbsent(topic) { AtomicInteger(0) }
            .incrementAndGet()
    }

    fun onTopicUnsubscribed(topic: StatsTopic) {
        topicCounters[topic]?.updateAndGet { count ->
            if (count > 0) count - 1 else 0
        }
    }

    fun getActiveUsersForTopic(topic: StatsTopic): Long =
        topicCounters[topic]?.get()?.toLong() ?: 0L

    /* ---------------- Debug / Monitoring ---------------- */

    fun snapshot(): Map<String, Long> =
        buildMap {
            put("totalActiveSessions", getTotalActiveSessions())
            StatsTopic.entries.forEach {
                put(it.name.lowercase(), getActiveUsersForTopic(it))
            }
        }
}