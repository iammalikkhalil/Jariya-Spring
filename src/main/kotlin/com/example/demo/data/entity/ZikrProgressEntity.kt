package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "zikr_progress")
data class ZikrProgressEntity(

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    // FK → user.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    val user: UserEntity,

    // FK → zikr.id (nullable)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zikr_id", referencedColumnName = "id")
    val zikr: ZikrEntity? = null,

    @Column(name = "device_id")
    val deviceId: UUID? = null,

    @Column(name = "session_id")
    val sessionId: UUID? = null,

    @Column(name = "source", columnDefinition = "TEXT")
    val source: String? = null,

    @Column(name = "count", nullable = false)
    val count: Int,

    @Column(name = "processed_levels")
    val processedLevels: Int? = null,

    @Column(name = "is_started", nullable = false)
    val isStarted: Boolean = false,

    @Column(name = "is_completed", nullable = false)
    val isCompleted: Boolean = false,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now(),

    @Column(name = "is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    val deletedAt: Instant? = null,

    @Column(name = "synced_at")
    val syncedAt: Instant? = null
)
