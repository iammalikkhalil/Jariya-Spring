package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "zikr_points",
    indexes = [
        Index(name = "idx_points_user", columnList = "user_id"),
        Index(name = "idx_points_zikr", columnList = "zikr_id"),
        Index(name = "idx_points_progress", columnList = "progress_id"),
        Index(name = "idx_points_deleted", columnList = "is_deleted"),
        Index(name = "idx_points_source_user", columnList = "source_user")
    ]
)
class ZikrPointEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    var user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zikr_id", referencedColumnName = "id")
    var zikr: ZikrEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progress_id", referencedColumnName = "id")
    var progress: ZikrProgressEntity? = null,

    @Column(name = "level", nullable = false)
    var level: Int,

    @Column(name = "points", nullable = false)
    var points: Int,

    @Column(name = "source_type", nullable = false, length = 50)
    var sourceType: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_user", referencedColumnName = "id", nullable = false)
    var sourceUser: UserEntity,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
)
