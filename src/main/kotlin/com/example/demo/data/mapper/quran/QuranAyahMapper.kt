package com.example.demo.data.mapper.quran

import com.example.demo.data.entity.quran.QuranAyahEntity
import com.example.demo.domain.model.quran.QuranAyahModel

fun QuranAyahEntity.toModel(): QuranAyahModel =
    QuranAyahModel(
        globalAyahId = globalAyahId,
        surahId = surahId,
        ayahNumberInSurah = ayahNumberInSurah,
        textArabic = textArabic,
        ayahKey = ayahKey,
        juzId = juzId,
        pageNumber = pageNumber,
        rukuId = rukuId,
        hizbQuarterId = hizbQuarterId,
        manzilId = manzilId,
        isSajda = isSajda,
        sajdaType = sajdaType,
        textArabicUthmani = textArabicUthmani,
        textArabicNoTashkeel = textArabicNoTashkeel,
        wordCount = wordCount,
        charCount = charCount,
////        createdAt = createdAt,
////        updatedAt = updatedAt,,
    )

fun QuranAyahModel.toEntity(): QuranAyahEntity =
    QuranAyahEntity(
        globalAyahId = globalAyahId,
        surahId = surahId,
        ayahNumberInSurah = ayahNumberInSurah,
        textArabic = textArabic,
        ayahKey = ayahKey,
        juzId = juzId,
        pageNumber = pageNumber,
        rukuId = rukuId,
        hizbQuarterId = hizbQuarterId,
        manzilId = manzilId,
        isSajda = isSajda,
        sajdaType = sajdaType,
        textArabicUthmani = textArabicUthmani,
        textArabicNoTashkeel = textArabicNoTashkeel,
        wordCount = wordCount,
        charCount = charCount,
//        createdAt = createdAt,
//        updatedAt = updatedAt,
    )
