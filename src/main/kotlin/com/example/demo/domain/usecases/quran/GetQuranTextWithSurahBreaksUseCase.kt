package com.example.demo.domain.usecases.quran

import com.example.demo.domain.model.quran.QuranTextWithSurahBreaksModel
import com.example.demo.domain.model.quran.QuranAyahModel
import org.springframework.stereotype.Service

@Service
class GetQuranTextWithSurahBreaksUseCase {

    fun execute(ayahList: List<QuranAyahModel>): QuranTextWithSurahBreaksModel {
        val textArabic = buildText(ayahList) // Build the Arabic text
        val textArabicNoTashkeel = buildTextNoTashkeel(ayahList) // Build the text without tashkeel
        return QuranTextWithSurahBreaksModel(textArabic, textArabicNoTashkeel) // Return as QuranTextWithSurahBreaksModel
    }

    private fun buildText(ayahList: List<QuranAyahModel>): String {
        return ayahList.joinToString(" ") { ayah ->
//            ayah.textArabic
            "${ayah.textArabic} [Surah ${ayah.surahId}:${ayah.ayahNumberInSurah}]"
        }.replace("\"", "") // Remove any extra double quotes
    }

    private fun buildTextNoTashkeel(ayahList: List<QuranAyahModel>): String {
        return ayahList.joinToString(" ") { ayah ->
            "${ayah.textArabicNoTashkeel}"
        }.replace("\"", "") // Remove any extra double quotes
    }
}