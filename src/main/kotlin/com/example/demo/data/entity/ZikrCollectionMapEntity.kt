package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "zikr_collection_map",
    indexes = [
        Index(name = "idx_zcm_zikr", columnList = "zikr_id"),
        Index(name = "idx_zcm_collection", columnList = "collection_id"),
        Index(name = "idx_zcm_order", columnList = "order_index"),
        Index(name = "idx_zcm_deleted", columnList = "is_deleted")
    ]
)
class ZikrCollectionMapEntity(

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    var id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zikr_id", referencedColumnName = "id", nullable = false)
    var zikr: ZikrEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", referencedColumnName = "id", nullable = false)
    var collection: ZikrCollectionEntity,

    @Column(name = "count_type", nullable = false, length = 10)
    var countType: String, // should become enum in future

    @Column(name = "count_value", nullable = false)
    var countValue: Int,

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
