package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "zikr_collection_map")
data class ZikrCollectionMapEntity(

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    // FK → zikr.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zikr_id", referencedColumnName = "id", nullable = false)
    val zikr: ZikrEntity,

    // FK → collection.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", referencedColumnName = "id", nullable = false)
    val collection: ZikrCollectionEntity,

    @Column(name = "count_type", nullable = false, columnDefinition = "TEXT")
    val countType: String, // "Up" or "Down"

    @Column(name = "count_value", nullable = false)
    val countValue: Int, // e.g., 33

    @Column(name = "order_index", nullable = false)
    val orderIndex: Int, // e.g., 1, 2, 3, 4

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now(),

    @Column(name = "is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    val deletedAt: Instant? = null
)
