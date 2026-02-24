package com.example.demo.domain.projection.points


import java.util.UUID

interface ZikrGoalPointsAggregateSummaryProjection {
    fun getGoalId(): UUID
    fun getTotalPoints(): Long
    fun getOriginalTargetCount(): Long
}
