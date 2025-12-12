package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrAudioRepository
import com.example.demo.presentation.dto.zikr.ZikrAudioDto
import com.example.demo.presentation.dto.zikr.ZikrAudioDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrAudioService(
    private val zikrAudioRepository: ZikrAudioRepository
) {

    fun getAllZikrAudios() =
        zikrAudioRepository.getAllZikrAudios()

    fun getZikrAudioById(id: String) =
        zikrAudioRepository.getZikrAudioById(id)

    fun createZikrAudio(body: ZikrAudioDtoRequest): Boolean =
        zikrAudioRepository.createZikrAudio(body.toDto().toDomain())

    fun updateZikrAudio(body: ZikrAudioDtoRequest): Boolean =
        zikrAudioRepository.updateZikrAudio(body.toDto().toDomain())

    fun deleteZikrAudio(id: String) =
        zikrAudioRepository.deleteZikrAudio(id)

    fun getUpdatedZikrAudios(updatedAt: Instant) =
        zikrAudioRepository.getUpdatedZikrAudios(updatedAt)


    // ------------------------------------------------------------
    // 🔹 Private extension to map Request → DTO cleanly
    // ------------------------------------------------------------
    private fun ZikrAudioDtoRequest.toDto() = ZikrAudioDto(
        id = this.id,
        zikrId = this.zikrId,
        audioUrl = this.audioUrl,
        languageCode = this.languageCode,
        duration = this.duration,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
}
