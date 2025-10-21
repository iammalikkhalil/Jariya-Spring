package com.example.demo.data.mapper.progress

import com.example.demo.domain.model.progress.ZikrPointSummaryModel


// Example DB projection or raw query result
data class ZikrPointSummaryProjection(
    val referralPoints: Int,
    val zikrPoints: Int,
    val totalZikrPoints: Int
)

// Mapper between projection and model
fun ZikrPointSummaryProjection.toModel(): ZikrPointSummaryModel =
    ZikrPointSummaryModel(
        referralPoints = referralPoints,
        zikrPoints = zikrPoints,
        totalZikrPoints = totalZikrPoints
    )
