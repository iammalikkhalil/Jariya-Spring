package com.example.demo.data.entity

import com.example.demo.domain.enums.zikr.GoalSourceType
import com.example.demo.domain.enums.zikr.GoalType
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "zikr_goal",
    indexes = [
        Index(name = "idx_goal_active_featured", columnList = "is_deleted, is_featured, show_from, show_until"),
        Index(name = "idx_goal_type", columnList = "type, is_deleted"),
        Index(name = "idx_goal_order", columnList = "order_index"),
        Index(name = "idx_goal_recurring", columnList = "is_recurring, is_deleted")
    ]
)
class ZikrGoalEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    @field:Size(max = 500)
    var title: String,

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "arabic_text", columnDefinition = "TEXT")
    var arabicText: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 50)
    var type: GoalType? = null,

    @field:Min(1)
    @Column(name = "target_value", nullable = false)
    var targetValue: Int,

    @field:Size(max = 50)
    @Column(name = "unit", length = 50)
    var unit: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", length = 50)
    var sourceType: GoalSourceType? = null,

    @Column(name = "source_ref", columnDefinition = "TEXT")
    var sourceRef: String? = null,

    @Column(name = "verified_by", columnDefinition = "TEXT")
    var verifiedBy: String? = null,

    @Column(name = "show_from")
    var showFrom: Instant? = null,

    @Column(name = "show_until")
    var showUntil: Instant? = null,

    @Column(name = "is_recurring", nullable = false)
    var isRecurring: Boolean = false,

    @Column(name = "recurrence", columnDefinition = "TEXT")
    var recurrence: String? = null,

    @Column(name = "is_featured", nullable = false)
    var isFeatured: Boolean = false,

    @Column(name = "order_index", nullable = false)
    var orderIndex: Int = 0,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
) {

    fun softDelete() {
        isDeleted = true
        deletedAt = Instant.now()
    }

    fun restore() {
        isDeleted = false
        deletedAt = null
    }

    fun isActive(): Boolean = !isDeleted
}
