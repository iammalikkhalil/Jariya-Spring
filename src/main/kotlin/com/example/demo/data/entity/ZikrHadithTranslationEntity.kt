package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "zikr_hadith_translations",
    indexes = [
        Index(name = "idx_hadith_translation_hadith", columnList = "hadith_id"),
        Index(name = "idx_hadith_translation_deleted", columnList = "is_deleted"),
        Index(name = "idx_hadith_translation_lang", columnList = "language_code")
    ]
)
class ZikrHadithTranslationEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hadith_id", referencedColumnName = "id", nullable = false)
    var hadith: ZikrHadithEntity,

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