package com.example.demo.data.entity.quran

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "quran_page")
data class QuranPageEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_number")
    val pageNumber: Long = 0,

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
