package com.example.demo.domain.model.zikr

import java.time.Instant

data class ZikrTagModel(

    val id: String,

    val text: String,

    val createdAt: Instant,
    val updatedAt: Instant,

    val isDeleted: Boolean,
    val deletedAt: Instant?
)
