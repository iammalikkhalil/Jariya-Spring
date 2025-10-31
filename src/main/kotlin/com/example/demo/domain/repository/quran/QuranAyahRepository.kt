package com.example.demo.domain.repository.quran


import com.example.demo.domain.model.quran.QuranAyahModel

interface QuranAyahRepository {
    // Custom query to fetch Ayahs by Surah and range of Ayah numbers
    fun findBySurahIdAndAyahNumberInSurahBetween(surahId: Int, start: Int, end: Int): List<QuranAyahModel>
}
