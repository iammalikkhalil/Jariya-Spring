package com.example.demo.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "zikr_audio_files",
    indexes = [
        Index(name = "idx_audio_zikr", columnList = "zikr_id"),
        Index(name = "idx_audio_deleted", columnList = "is_deleted")
    ]
)
class ZikrAudioEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zikr_id", referencedColumnName = "id", nullable = false)
    var zikr: ZikrEntity,

    @Column(name = "audio_url", nullable = false, columnDefinition = "TEXT")
    var audioUrl: String,

    @Column(name = "language_code", nullable = false, length = 10)
    var languageCode: String,

    @Min(1)
    @Column(name = "duration", nullable = false)
    var duration: Int,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
)
