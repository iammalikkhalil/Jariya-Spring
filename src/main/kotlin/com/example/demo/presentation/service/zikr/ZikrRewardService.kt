package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrRewardRepository
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.*
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrRewardService(
    private val zikrRewardRepository: ZikrRewardRepository
) {

    fun getAllZikrRewards() =
        zikrRewardRepository.getAllZikrRewards()

    fun getZikrRewardById(id: String) =
        zikrRewardRepository.getZikrRewardById(id)

    fun createZikrReward(body: ZikrRewardDtoRequest): Boolean =
        zikrRewardRepository.createZikrReward(
            body.toDto().toDomain()
        )

    fun updateZikrReward(body: ZikrRewardDtoRequest): Boolean =
        zikrRewardRepository.updateZikrReward(
            body.toDto().toDomain()
        )

    fun deleteZikrReward(id: String) =
        zikrRewardRepository.deleteZikrReward(id)

    fun getUpdatedZikrRewards(updatedAt: Instant) =
        zikrRewardRepository.getUpdatedZikrRewards(updatedAt)


    // ------------------------------------------------------------
    // 🔹 PRIVATE MAPPER: Request → DTO
    // ------------------------------------------------------------
    private fun ZikrRewardDtoRequest.toDto() = ZikrRewardDto(
        id = this.id ?: generateUUID(),
        zikrId = this.zikrId,
        text = this.text,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = this.updatedAt ?: Instant.now(),
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
}
