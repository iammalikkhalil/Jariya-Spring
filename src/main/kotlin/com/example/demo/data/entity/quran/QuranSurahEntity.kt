package com.example.demo.data.entity.quran

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "quran_surah")
data class QuranSurahEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "surah_id")
    val surahId: Long = 0,

    @Column(name = "name_arabic", nullable = false, columnDefinition = "TEXT")
    val nameArabic: String,

    @Column(name = "name_transliteration", nullable = false, length = 100)
    val nameTransliteration: String,

    @Column(name = "name_english", nullable = false, length = 150)
    val nameEnglish: String,

    @Column(name = "ayah_count", nullable = false)
    val ayahCount: Int,

    @Column(name = "revelation_place", nullable = false)
    val revelationPlace: String, // "Meccan" or "Medinan"

    @Column(name = "chronological_order", nullable = false)
    val chronologicalOrder: Int,

    @Column(name = "ruku_count", nullable = false)
    val rukuCount: Int,

    @Column(name = "has_bismillah", nullable = false)
    val hasBismillah: Boolean = true,

    @Column(name = "mukattaat_letters", columnDefinition = "TEXT")
    val mukattaatLetters: String? = null,

    @Column(name = "start_global_ayah_id", nullable = false)
    val startGlobalAyahId: Int,

    @Column(name = "end_global_ayah_id", nullable = false)
    val endGlobalAyahId: Int,

//    @Column(name = "created_at", nullable = false)
//    val createdAt: Instant = Instant.now(),
//
//    @Column(name = "updated_at", nullable = false)
//    val updatedAt: Instant = Instant.now()
)
