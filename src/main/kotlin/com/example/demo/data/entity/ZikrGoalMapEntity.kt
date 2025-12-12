package com.example.demo.data.entity

import com.example.demo.domain.enums.zikr.GoalCountType
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "zikr_goal_map",
    indexes = [
        Index(name = "uk_goal_zikr", columnList = "goal_id, zikr_id", unique = true),
        Index(name = "idx_zikr_goal_map_zikr", columnList = "zikr_id, is_deleted"),
        Index(name = "idx_zikr_goal_map_goal", columnList = "goal_id, order_index, is_deleted"),
        Index(name = "idx_zikr_goal_map_active", columnList = "is_deleted, order_index")
    ]
)
class ZikrGoalMapEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "zikr_id",
        referencedColumnName = "id",
        nullable = false,
        foreignKey = ForeignKey(name = "fk_zikr_goal_map_zikr")
    )
    var zikr: ZikrEntity,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "goal_id",
        referencedColumnName = "id",
        nullable = false,
        foreignKey = ForeignKey(name = "fk_zikr_goal_map_goal")
    )
    var goal: ZikrGoalEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "count_type", nullable = false, length = 20)
    var countType: GoalCountType,

    @Positive
    @Min(1)
    @Column(name = "count_value", nullable = false)
    var countValue: Int,

    @Min(0)
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
) {

    fun isActive(): Boolean = !isDeleted
}
