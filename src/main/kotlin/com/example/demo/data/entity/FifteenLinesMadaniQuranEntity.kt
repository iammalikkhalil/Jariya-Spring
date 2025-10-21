package com.example.demo.data.entity

import jakarta.persistence.*
import java.io.Serializable
import java.time.Instant

@Entity
@Table(name = "fifteen_lines_madani_quran")
@IdClass(FifteenLinesMadaniQuranEntity.FifteenLinesMadaniQuranId::class)
data class FifteenLinesMadaniQuranEntity(

    @Id
    @Column(name = "page_number", nullable = false)
    val pageNumber: Int,

    @Id
    @Column(name = "line_number", nullable = false)
    val lineNumber: Int,

    @Column(name = "text_ar", nullable = false, columnDefinition = "TEXT")
    val textAr: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now()
){
    data class FifteenLinesMadaniQuranId(
        val pageNumber: Int = 0,
        val lineNumber: Int = 0
    ) : Serializable
}
