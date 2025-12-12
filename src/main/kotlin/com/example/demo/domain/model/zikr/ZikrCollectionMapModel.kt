package com.example.demo.domain.model.zikr

import com.example.demo.domain.enums.zikr.GoalCountType
import java.time.Instant

data class ZikrCollectionMapModel(

    val id: String,

    val zikrId: String,
    val collectionId: String,

    val countType: String,
    val countValue: Int,
    val orderIndex: Int,

    val createdAt: Instant,
    val updatedAt: Instant,

    val isDeleted: Boolean,
    val deletedAt: Instant?
)
