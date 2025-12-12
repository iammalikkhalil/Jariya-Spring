package com.example.demo.domain.model.zikr

import java.time.Instant

data class ZikrCollectionModel(

    val id: String,

    val text: String,
    val isFeatured: Boolean,
    val description: String?,
    val orderIndex: Int,

    val createdAt: Instant,
    val updatedAt: Instant,

    val isDeleted: Boolean,
    val deletedAt: Instant?
)
