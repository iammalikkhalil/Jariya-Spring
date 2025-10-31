package com.example.demo.data.mapper.quran

import com.example.demo.data.entity.quran.QuranRukuEntity
import com.example.demo.domain.model.quran.QuranRukuModel

fun QuranRukuEntity.toModel(): QuranRukuModel =
    QuranRukuModel(
        rukuId = rukuId,
        surahId = surahId,
        rukuNumberInSurah = rukuNumberInSurah,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )

fun QuranRukuModel.toEntity(): QuranRukuEntity =
    QuranRukuEntity(
        rukuId = rukuId,
        surahId = surahId,
        rukuNumberInSurah = rukuNumberInSurah,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )
