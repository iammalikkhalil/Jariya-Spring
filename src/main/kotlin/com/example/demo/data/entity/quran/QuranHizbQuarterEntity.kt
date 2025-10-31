package com.example.demo.data.entity.quran

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "quran_hizb_quarters")
data class QuranHizbQuarterEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hizb_quarter_id")
    val hizbQuarterId: Long = 0,

    @Column(name = "hizb_number", nullable = false)
    val hizbNumber: Int,

    @Column(name = "quarter_number_in_hizb", nullable = false)
    val quarterNumberInHizb: Int,

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
