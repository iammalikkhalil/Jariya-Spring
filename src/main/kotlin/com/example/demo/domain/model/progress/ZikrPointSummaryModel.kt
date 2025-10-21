package com.example.demo.domain.model.progress

import com.example.demo.presentation.dto.progress.ZikrPointSummaryDto


data class ZikrPointSummaryModel(
    val referralPoints: Int,
    val zikrPoints: Int,
    val totalZikrPoints: Int
)

fun ZikrPointSummaryModel.toDto() = ZikrPointSummaryDto(
    referralPoints = this.referralPoints,
    zikrPoints = this.zikrPoints,
    totalZikrPoints = this.totalZikrPoints
)