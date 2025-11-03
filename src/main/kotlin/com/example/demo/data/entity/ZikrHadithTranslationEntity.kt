package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "zikr_hadith_translations")
data class ZikrHadithTranslationEntity(

    @Id
//    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    // FK → zikr_hadiths.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hadith_id", referencedColumnName = "id", nullable = false)
    val hadith: ZikrHadithEntity,

    @Column(name = "translation", nullable = false, columnDefinition = "TEXT")
    val translation: String,

    @Column(name = "language_code", nullable = false, columnDefinition = "TEXT")
    val languageCode: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now(),

    @Column(name = "is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    val deletedAt: Instant? = null
)
