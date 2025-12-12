package com.example.demo.domain.model.zikr

import java.time.Instant

data class ZikrTagMapModel(

    val id: String,

    val zikrId: String,
    val tagId: String,

    val createdAt: Instant,
    val updatedAt: Instant,

    val isDeleted: Boolean,
    val deletedAt: Instant?
)
