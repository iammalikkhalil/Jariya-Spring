package com.example.demo.data.entity.quran

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "quran_ayahs")
data class QuranAyahEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "global_ayah_id")
    val globalAyahId: Long = 0,

    @Column(name = "surah_id", nullable = false)
    val surahId: Int,

    @Column(name = "ayah_number_in_surah", nullable = false)
    val ayahNumberInSurah: Int,

    @Column(name = "text_arabic", columnDefinition = "TEXT", nullable = false)
    val textArabic: String,

    @Column(name = "ayah_key", nullable = false)
    val ayahKey: String,

    @Column(name = "juz_id", nullable = false)
    val juzId: Int,

    @Column(name = "page_number", nullable = false)
    val pageNumber: Int,

    @Column(name = "ruku_id", nullable = false)
    val rukuId: Int,

    @Column(name = "hizb_quarter_id", nullable = false)
    val hizbQuarterId: Int,

    @Column(name = "manzil_id", nullable = false)
    val manzilId: Int,

    @Column(name = "is_sajda", nullable = false)
    val isSajda: Boolean = false,

    @Column(name = "sajda_type")
    val sajdaType: String? = null,

    @Column(name = "text_arabic_uthmani", columnDefinition = "TEXT")
    val textArabicUthmani: String? = null,

    @Column(name = "text_arabic_no_tashkeel", columnDefinition = "TEXT")
    val textArabicNoTashkeel: String? = null,

    @Column(name = "word_count", nullable = false)
    val wordCount: Int,

    @Column(name = "char_count", nullable = false)
    val charCount: Int,

//    @Column(name = "created_at", nullable = false)
//    val createdAt: Instant = Instant.now(),
//
//    @Column(name = "updated_at", nullable = false)
//    val updatedAt: Instant = Instant.now()
)
