package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrGoalRepository
import com.example.demo.presentation.dto.zikr.ZikrGoalDto
import com.example.demo.data.mapper.zikr.toModel
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrGoalService(
    private val zikrGoalRepository: ZikrGoalRepository
) {

    fun getAllZikrGoals() =
        zikrGoalRepository.getAllGoals()

    fun getZikrGoalById(id: String) =
        zikrGoalRepository.getGoalById(id)

    fun createZikrGoal(body: ZikrGoalDto): Boolean {
        // CREATE: id null allowed, mapper will generate UUID
        val model = body.toModel()
        return zikrGoalRepository.createGoal(model)
    }

    fun updateZikrGoal(body: ZikrGoalDto): Boolean {
        // UPDATE: id MUST be present; warna new row ban jayega (prevent)
        val id = body.id ?: return false
        if (id.isBlank()) return false

        val model = body.toModel()
        return zikrGoalRepository.updateGoal(model)
    }

    fun deleteZikrGoal(id: String?): Boolean {
        if (id.isNullOrBlank()) return false
        return zikrGoalRepository.deleteGoal(id)
    }

    fun getUpdatedZikrGoals(updatedAt: Instant) =
        zikrGoalRepository.getUpdatedGoals(updatedAt)
}
