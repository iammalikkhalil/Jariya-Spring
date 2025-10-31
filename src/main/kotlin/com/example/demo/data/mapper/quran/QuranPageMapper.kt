package com.example.demo.data.mapper.quran

import com.example.demo.data.entity.quran.QuranPageEntity
import com.example.demo.domain.model.quran.QuranPageModel

fun QuranPageEntity.toModel(): QuranPageModel =
    QuranPageModel(
        pageNumber = pageNumber,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )

fun QuranPageModel.toEntity(): QuranPageEntity =
    QuranPageEntity(
        pageNumber = pageNumber,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )
