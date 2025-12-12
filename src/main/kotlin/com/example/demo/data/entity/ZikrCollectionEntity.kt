package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "zikr_collections",
    indexes = [
        Index(name = "idx_collection_deleted", columnList = "is_deleted"),
        Index(name = "idx_collection_order", columnList = "order_index"),
        Index(name = "idx_collection_featured", columnList = "is_featured")
    ]
)
class ZikrCollectionEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,

    @Column(name = "is_featured", nullable = false)
    var isFeatured: Boolean = false,

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    var text: String,

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "order_index", nullable = false)
    var orderIndex: Int,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
)
