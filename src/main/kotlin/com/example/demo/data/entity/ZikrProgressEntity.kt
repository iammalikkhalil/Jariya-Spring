package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "zikr_progress",
    indexes = [
        Index(name = "idx_progress_user", columnList = "user_id"),
        Index(name = "idx_progress_zikr", columnList = "zikr_id"),
        Index(name = "idx_progress_deleted", columnList = "is_deleted"),
        Index(name = "idx_progress_started", columnList = "is_started"),
        Index(name = "idx_progress_completed", columnList = "is_completed")
    ]
)
class ZikrProgressEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    var user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zikr_id", referencedColumnName = "id")
    var zikr: ZikrEntity? = null,

    @Column(name = "device_id")
    var deviceId: UUID? = null,

    @Column(name = "session_id")
    var sessionId: UUID? = null,

    @Column(name = "source", columnDefinition = "TEXT")
    var source: String? = null,

    @Column(name = "count", nullable = false)
    var count: Int,

    @Column(name = "processed_levels")
    var processedLevels: Int? = null,

    @Column(name = "is_started", nullable = false)
    var isStarted: Boolean = false,

    @Column(name = "is_completed", nullable = false)
    var isCompleted: Boolean = false,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null,

    @Column(name = "synced_at")
    var syncedAt: Instant? = null
)
