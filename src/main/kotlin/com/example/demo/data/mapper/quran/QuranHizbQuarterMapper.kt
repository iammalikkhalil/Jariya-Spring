package com.example.demo.data.mapper.quran

import com.example.demo.data.entity.quran.QuranHizbQuarterEntity
import com.example.demo.domain.model.quran.QuranHizbQuarterModel

fun QuranHizbQuarterEntity.toModel(): QuranHizbQuarterModel =
    QuranHizbQuarterModel(
        hizbQuarterId = hizbQuarterId,
        hizbNumber = hizbNumber,
        quarterNumberInHizb = quarterNumberInHizb,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )

fun QuranHizbQuarterModel.toEntity(): QuranHizbQuarterEntity =
    QuranHizbQuarterEntity(
        hizbQuarterId = hizbQuarterId,
        hizbNumber = hizbNumber,
        quarterNumberInHizb = quarterNumberInHizb,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )
