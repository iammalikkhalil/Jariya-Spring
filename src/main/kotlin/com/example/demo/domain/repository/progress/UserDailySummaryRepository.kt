package com.example.demo.domain.repository.progress

import com.example.demo.domain.model.progress.UserDailySummaryModel


interface UserDailySummaryRepository {
     fun getAllUserDailySummaries(): List<UserDailySummaryModel>
     fun getUserDailySummaryById(id: String): UserDailySummaryModel?
     fun getUserDailySummaryByUserId(id: String): UserDailySummaryModel?
     fun createUserDailySummary(userDailySummary: UserDailySummaryModel): Boolean
}