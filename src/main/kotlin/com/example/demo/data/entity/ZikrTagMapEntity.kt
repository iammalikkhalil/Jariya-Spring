package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "zikr_tag_map")
data class ZikrTagMapEntity(

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    // FK → zikr.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zikr_id", referencedColumnName = "id", nullable = false)
    val zikr: ZikrEntity,

    // FK → tag.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)
    val tag: ZikrTagEntity,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now(),

    @Column(name = "is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    val deletedAt: Instant? = null
)
