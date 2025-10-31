package com.example.demo.data.mapper.quran

import com.example.demo.data.entity.quran.QuranManzilEntity
import com.example.demo.domain.model.quran.QuranManzilModel

fun QuranManzilEntity.toModel(): QuranManzilModel =
    QuranManzilModel(
        manzilId = manzilId,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )

fun QuranManzilModel.toEntity(): QuranManzilEntity =
    QuranManzilEntity(
        manzilId = manzilId,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )
