package com.example.demo.domain.model.zikr

import java.time.Instant

data class ZikrQualityModel(

    val id: String,

    val zikrId: String,
    val text: String,

    val createdAt: Instant,
    val updatedAt: Instant,

    val isDeleted: Boolean,
    val deletedAt: Instant?
)
