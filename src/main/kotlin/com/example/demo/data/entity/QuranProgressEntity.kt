package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "quran_progress",
    indexes = [
        Index(name = "idx_quran_progress_user", columnList = "user_id"),
        Index(name = "idx_quran_progress_surah", columnList = "surah_id"),
        Index(name = "idx_quran_progress_ayah", columnList = "ayah_id"),
        Index(name = "idx_quran_progress_deleted", columnList = "is_deleted"),
        Index(name = "idx_quran_progress_started", columnList = "is_started"),
        Index(name = "idx_quran_progress_completed", columnList = "is_completed")
    ]
)
class QuranProgressEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,

    @Column(name = "user_id", nullable = true)
    var user: String? = null,

    @Column(name = "surah_id", nullable = true)
    var surahId: Int? = null,

    @Column(name = "ayah_id")
    var ayahId: Int,

    @Column(name = "device_id")
    var deviceId: UUID? = null,

    @Column(name = "session_id")
    var sessionId: UUID? = null,

    @Column(name = "source", columnDefinition = "TEXT")
    var source: String? = null,

    @Column(name = "count", nullable = false)
    var count: Int,

    @Column(name = "char_count", nullable = true)
    var charCount: Int? = 0,

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