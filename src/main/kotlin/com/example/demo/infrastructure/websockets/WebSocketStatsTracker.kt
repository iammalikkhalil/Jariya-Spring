package com.example.demo.infrastructure.websockets

import com.example.demo.domain.enums.websockets.StatsTopic
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger


object WebSocketStatsTracker {

    private val totalActiveSessions = AtomicInteger(0)

    private val topicCounters: MutableMap<StatsTopic, AtomicInteger> =
        ConcurrentHashMap()

    // 🔑 sessionId -> subscribed topics
    private val sessionTopics: MutableMap<String, MutableSet<StatsTopic>> =
        ConcurrentHashMap()

    /* ---------------- Session Level ---------------- */

    fun onSessionConnected(sessionId: String) {
        totalActiveSessions.incrementAndGet()
        sessionTopics[sessionId] = ConcurrentHashMap.newKeySet()
    }

    fun onSessionDisconnected(sessionId: String) {
        // 🔥 CLEAN ALL TOPICS OWNED BY THIS SESSION
        sessionTopics.remove(sessionId)?.forEach { topic ->
            topicCounters[topic]?.updateAndGet { count ->
                if (count > 0) count - 1 else 0
            }
        }

        totalActiveSessions.updateAndGet { count ->
            if (count > 0) count - 1 else 0
        }
    }

    fun getTotalActiveSessions(): Long =
        totalActiveSessions.get().toLong()

    /* ---------------- Topic Level ---------------- */

    fun onTopicSubscribed(sessionId: String, topic: StatsTopic) {
        val topics = sessionTopics.computeIfAbsent(sessionId) {
            ConcurrentHashMap.newKeySet()
        }

        // ✅ prevent double counting
        if (topics.add(topic)) {
            topicCounters
                .computeIfAbsent(topic) { AtomicInteger(0) }
                .incrementAndGet()
        }
    }

    fun onTopicUnsubscribed(sessionId: String, topic: StatsTopic) {
        sessionTopics[sessionId]?.let { topics ->
            if (topics.remove(topic)) {
                topicCounters[topic]?.updateAndGet { count ->
                    if (count > 0) count - 1 else 0
                }
            }
        }
    }

    fun getActiveUsersForTopic(topic: StatsTopic): Long =
        topicCounters[topic]?.get()?.toLong() ?: 0L

    /* ---------------- Debug ---------------- */

    fun snapshot(): Map<String, Long> =
        buildMap {
            put("totalActiveSessions", getTotalActiveSessions())
            StatsTopic.entries.forEach {
                put(it.name.lowercase(), getActiveUsersForTopic(it))
            }
        }
}
