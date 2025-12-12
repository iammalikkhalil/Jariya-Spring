package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(
    name = "zikrs",
    indexes = [
        Index(name = "idx_zikr_deleted", columnList = "is_deleted"),
        Index(name = "idx_zikr_verified", columnList = "is_verified"),
        Index(name = "idx_zikr_quran", columnList = "is_quran"),
        Index(name = "idx_zikr_hadith", columnList = "is_hadith"),
        Index(name = "idx_zikr_char_count", columnList = "char_count")
    ]
)
class ZikrEntity(

    @Id
    @Column(name = "id", nullable = false, unique = true)
    var id: UUID,

    @Column(name = "text_ar", nullable = false, columnDefinition = "TEXT")
    var textAr: String,

    @Column(name = "title_en", columnDefinition = "TEXT")
    var titleEn: String? = null,

    @Column(name = "title_ur", columnDefinition = "TEXT")
    var titleUr: String? = null,

    @Column(name = "transliteration", columnDefinition = "TEXT")
    var transliteration: String? = null,

    @Column(name = "quantity_notes", columnDefinition = "TEXT")
    var quantityNotes: String? = null,

    @Column(name = "source_notes", columnDefinition = "TEXT")
    var sourceNotes: String? = null,

    @Column(name = "is_quran", nullable = false)
    var isQuran: Boolean = false,

    @Column(name = "is_hadith", nullable = false)
    var isHadith: Boolean = false,

    @Column(name = "is_verified", nullable = false)
    var isVerified: Boolean = false,

    @Column(name = "verified_by_name", columnDefinition = "TEXT")
    var verifiedByName: String? = null,

    @Column(name = "char_count", nullable = false)
    var charCount: Int,

    @Column(name = "verified_date")
    var verifiedDate: Instant? = null,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
)
