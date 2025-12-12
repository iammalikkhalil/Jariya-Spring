package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "zikr_translations",
    indexes = [
        Index(name = "idx_translation_zikr", columnList = "zikr_id"),
        Index(name = "idx_translation_deleted", columnList = "is_deleted"),
        Index(name = "idx_translation_lang", columnList = "language_code")
    ]
)
class ZikrTranslationEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zikr_id", referencedColumnName = "id", nullable = false)
    var zikr: ZikrEntity,

    @Column(name = "translation", nullable = false, columnDefinition = "TEXT")
    var translation: String,

    @Column(name = "language_code", nullable = false, length = 10)
    var languageCode: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
)
