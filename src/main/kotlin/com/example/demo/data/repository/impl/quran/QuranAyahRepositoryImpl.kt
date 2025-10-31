package com.example.demo.data.repository.impl.quran

import com.example.demo.data.entity.quran.QuranAyahEntity
import com.example.demo.data.mapper.quran.toModel
import com.example.demo.data.repository.jpa.quran.QuranAyahJpaRepository
import com.example.demo.domain.model.quran.QuranAyahModel
import com.example.demo.domain.repository.quran.QuranAyahRepository
import com.example.demo.infrastructure.utils.Log
import org.springframework.stereotype.Repository

@Repository
class QuranAyahRepositoryImpl(
    private val quranAyahJpaRepository: QuranAyahJpaRepository
) : QuranAyahRepository {

    // Fetch Ayahs by Surah ID and range of Ayah numbers
    override fun findBySurahIdAndAyahNumberInSurahBetween(surahId: Int, start: Int, end: Int): List<QuranAyahModel> =
        try {
            val ayahs = quranAyahJpaRepository.findBySurahIdAndAyahNumberInSurahBetween(surahId, start, end)
            ayahs.map { it.toModel() }
        } catch (e: Exception) {
            Log.error("❌ Error fetching Ayahs: ${e.message}", e)
            emptyList()
        }
}
