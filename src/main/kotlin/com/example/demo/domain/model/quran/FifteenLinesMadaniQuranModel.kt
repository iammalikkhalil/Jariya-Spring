package com.example.demo.domain.model.quran

import com.example.demo.presentation.dto.quran.FifteenLinesMadaniQuranDto
import java.time.Instant


data class FifteenLinesMadaniQuranModel(
    val pageNumber: Int,
    val lineNumber: Int,
    val textAr: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)

fun FifteenLinesMadaniQuranModel.toDto() : FifteenLinesMadaniQuranDto = FifteenLinesMadaniQuranDto(
    pageNumber = this.pageNumber,
    lineNumber = this.lineNumber,
    textAr = this.textAr,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,

    )