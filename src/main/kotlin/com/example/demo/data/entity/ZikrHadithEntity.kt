package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "zikr_hadiths",
    indexes = [
        Index(name = "idx_hadith_zikr", columnList = "zikr_id"),
        Index(name = "idx_hadith_deleted", columnList = "is_deleted")
    ]
)
class ZikrHadithEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zikr_id", referencedColumnName = "id", nullable = false)
    var zikr: ZikrEntity,

    @Column(name = "text_ar", nullable = false, columnDefinition = "TEXT")
    var textAr: String,

    @Column(name = "reference", nullable = false, columnDefinition = "TEXT")
    var reference: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
)