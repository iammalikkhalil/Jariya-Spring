package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrRewardRepository
import com.example.demo.presentation.dto.zikr.ZikrRewardDto
import com.example.demo.presentation.dto.zikr.ZikrRewardDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrRewardService(
    private val zikrRewardRepository: ZikrRewardRepository
) {

    fun getAllZikrRewards() = zikrRewardRepository.getAllZikrRewards()

    fun getZikrRewardById(id: String) = zikrRewardRepository.getZikrRewardById(id)

    fun createZikrReward(body: ZikrRewardDtoRequest): Boolean {
        val dto = ZikrRewardDto(
            id = body.id,
            zikrId = body.zikrId,
            text = body.text,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrRewardRepository.createZikrReward(dto.toDomain())
    }

    fun updateZikrReward(body: ZikrRewardDtoRequest): Boolean {
        val dto = ZikrRewardDto(
            id = body.id,
            zikrId = body.zikrId,
            text = body.text,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrRewardRepository.updateZikrReward(dto.toDomain())
    }

    fun deleteZikrReward(id: String) = zikrRewardRepository.deleteZikrReward(id)

    fun getUpdatedZikrRewards(updatedAt: Instant) =
        zikrRewardRepository.getUpdatedZikrRewards(updatedAt)
}
