package com.example.demo.data.mapper.quran

import com.example.demo.data.entity.FifteenLinesMadaniQuranEntity
import com.example.demo.domain.model.quran.FifteenLinesMadaniQuranModel

fun FifteenLinesMadaniQuranEntity.toModel(): FifteenLinesMadaniQuranModel =
    FifteenLinesMadaniQuranModel(
        pageNumber = pageNumber,
        lineNumber = lineNumber,
        textAr = textAr,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun FifteenLinesMadaniQuranModel.toEntity(): FifteenLinesMadaniQuranEntity =
    FifteenLinesMadaniQuranEntity(
        pageNumber = pageNumber,
        lineNumber = lineNumber,
        textAr = textAr,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
