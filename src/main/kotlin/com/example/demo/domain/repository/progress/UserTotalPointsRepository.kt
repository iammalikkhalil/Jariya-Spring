package com.example.demo.domain.repository.progress

import com.example.demo.domain.model.progress.UserTotalPointsModel


interface UserTotalPointsRepository {
     fun getAllUserTotalPoints(): List<UserTotalPointsModel>
     fun getUserTotalPointById(id: String): UserTotalPointsModel?
     fun createUserTotalPoint(userTotalPoint: UserTotalPointsModel): Boolean
}