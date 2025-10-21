package com.example.demo.presentation.dto.quran

import java.time.Instant.now
import java.time.Instant

import com.example.demo.domain.model.quran.FifteenLinesMadaniQuranModel


data class FifteenLinesMadaniQuranDto(
    val pageNumber: Int,
    val lineNumber: Int,
    val textAr: String,
    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
)

fun FifteenLinesMadaniQuranDto.toDomain() : FifteenLinesMadaniQuranModel = FifteenLinesMadaniQuranModel(
    pageNumber = this.pageNumber,
    lineNumber = this.lineNumber,
    textAr = this.textAr,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,

)