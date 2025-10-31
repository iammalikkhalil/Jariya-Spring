package com.example.demo.data.repository.jpa.quran

import com.example.demo.data.entity.quran.QuranAyahEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface QuranAyahJpaRepository : JpaRepository<QuranAyahEntity, Long> {

    // Find Ayahs by Surah ID and range of Ayah numbers
    fun findBySurahIdAndAyahNumberInSurahBetween(
        @Param("surahId") surahId: Int,
        @Param("start") start: Int,
        @Param("end") end: Int
    ): List<QuranAyahEntity>
}
