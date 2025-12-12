package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrGoalMapRepository
import com.example.demo.presentation.dto.zikr.ZikrGoalMapDto
import com.example.demo.data.mapper.zikr.toModel
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrGoalMapService(
    private val zikrGoalMapRepository: ZikrGoalMapRepository
) {

    fun getAllZikrGoalMaps() =
        zikrGoalMapRepository.getAllGoalMaps()

    fun getZikrGoalMapById(id: String) =
        zikrGoalMapRepository.getGoalMapById(id)

    fun createZikrGoalMap(body: ZikrGoalMapDto): Boolean {
        val model = body.toModel()
        return zikrGoalMapRepository.createGoalMap(model)
    }

    fun updateZikrGoalMap(body: ZikrGoalMapDto): Boolean {
        val id = body.id ?: return false
        if (id.isBlank()) return false

        val model = body.toModel()
        return zikrGoalMapRepository.updateGoalMap(model)
    }

    fun deleteZikrGoalMap(id: String?): Boolean {
        if (id.isNullOrBlank()) return false
        return zikrGoalMapRepository.deleteGoalMap(id)
    }

    fun getUpdatedZikrGoalMaps(updatedAt: Instant) =
        zikrGoalMapRepository.getUpdatedGoalMaps(updatedAt)
}
