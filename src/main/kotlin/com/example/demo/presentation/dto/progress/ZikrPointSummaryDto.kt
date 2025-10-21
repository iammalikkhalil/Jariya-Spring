package com.example.demo.presentation.dto.progress


import com.example.demo.domain.model.progress.ZikrPointSummaryModel


data class ZikrPointSummaryDto(
    val referralPoints: Int,
    val zikrPoints: Int,
    val totalZikrPoints: Int
)

fun ZikrPointSummaryDto.toDomain() = ZikrPointSummaryModel(
    referralPoints = this.referralPoints,
    zikrPoints = this.zikrPoints,
    totalZikrPoints = this.totalZikrPoints
)