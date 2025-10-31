package com.example.demo.data.mapper.quran

import com.example.demo.data.entity.quran.QuranJuzEntity
import com.example.demo.domain.model.quran.QuranJuzModel

fun QuranJuzEntity.toModel(): QuranJuzModel =
    QuranJuzModel(
        juzId = juzId,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )

fun QuranJuzModel.toEntity(): QuranJuzEntity =
    QuranJuzEntity(
        juzId = juzId,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )
