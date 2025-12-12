package com.example.demo.domain.model.zikr

import java.time.Instant

data class ZikrHadithModel(

    val id: String,

    val zikrId: String,
    val textAr: String,
    val reference: String,

    val createdAt: Instant,
    val updatedAt: Instant,

    val isDeleted: Boolean,
    val deletedAt: Instant?
)
