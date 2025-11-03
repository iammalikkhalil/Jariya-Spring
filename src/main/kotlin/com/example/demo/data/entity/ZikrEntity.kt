package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "zikrs")
data class ZikrEntity(

    @Id
//    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "text_ar", nullable = false, columnDefinition = "TEXT")
    val textAr: String,

    @Column(name = "title_en", columnDefinition = "TEXT")
    val titleEn: String? = null,

    @Column(name = "title_ur", columnDefinition = "TEXT")
    val titleUr: String? = null,

    @Column(name = "transliteration", columnDefinition = "TEXT")
    val transliteration: String? = null,

    @Column(name = "quantity_notes", columnDefinition = "TEXT")
    val quantityNotes: String? = null,

    @Column(name = "source_notes", columnDefinition = "TEXT")
    val sourceNotes: String? = null,

    @Column(name = "is_quran", nullable = false)
    val isQuran: Boolean = false,

    @Column(name = "is_hadith", nullable = false)
    val isHadith: Boolean = false,

    @Column(name = "is_verified", nullable = false)
    val isVerified: Boolean = false,

    @Column(name = "verified_by_name", columnDefinition = "TEXT")
    val verifiedByName: String? = null,

    @Column(name = "char_count", nullable = false)
    val charCount: Int,

    @Column(name = "verified_date")
    val verifiedDate: Instant? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now(),

    @Column(name = "is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    val deletedAt: Instant? = null
)
