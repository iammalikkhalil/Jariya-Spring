package com.example.demo.data.mapper.quran

import com.example.demo.data.entity.quran.QuranSurahEntity
import com.example.demo.domain.model.quran.QuranSurahModel

fun QuranSurahEntity.toModel(): QuranSurahModel =
    QuranSurahModel(
        surahId = surahId,
        nameArabic = nameArabic,
        nameTransliteration = nameTransliteration,
        nameEnglish = nameEnglish,
        ayahCount = ayahCount,
        revelationPlace = revelationPlace,
        chronologicalOrder = chronologicalOrder,
        rukuCount = rukuCount,
        hasBismillah = hasBismillah,
        mukattaatLetters = mukattaatLetters,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )

fun QuranSurahModel.toEntity(): QuranSurahEntity =
    QuranSurahEntity(
        surahId = surahId,
        nameArabic = nameArabic,
        nameTransliteration = nameTransliteration,
        nameEnglish = nameEnglish,
        ayahCount = ayahCount,
        revelationPlace = revelationPlace,
        chronologicalOrder = chronologicalOrder,
        rukuCount = rukuCount,
        hasBismillah = hasBismillah,
        mukattaatLetters = mukattaatLetters,
        startGlobalAyahId = startGlobalAyahId,
        endGlobalAyahId = endGlobalAyahId,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )
