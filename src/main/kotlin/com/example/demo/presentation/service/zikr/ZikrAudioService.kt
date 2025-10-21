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

    fun getAllZikrAudios() = zikrAudioRepository.getAllZikrAudios()

    fun getZikrAudioById(id: String) = zikrAudioRepository.getZikrAudioById(id)

    fun createZikrAudio(body: ZikrAudioDtoRequest): Boolean {
        val dto = ZikrAudioDto(
            id = body.id,
            zikrId = body.zikrId,
            audioUrl = body.audioUrl,
            languageCode = body.languageCode,
            duration = body.duration,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrAudioRepository.createZikrAudio(dto.toDomain())
    }

    fun updateZikrAudio(body: ZikrAudioDtoRequest): Boolean {
        val dto = ZikrAudioDto(
            id = body.id,
            zikrId = body.zikrId,
            audioUrl = body.audioUrl,
            languageCode = body.languageCode,
            duration = body.duration,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrAudioRepository.updateZikrAudio(dto.toDomain())
    }

    fun deleteZikrAudio(id: String) = zikrAudioRepository.deleteZikrAudio(id)

    fun getUpdatedZikrAudios(updatedAt: Instant) =
        zikrAudioRepository.getUpdatedZikrAudios(updatedAt)
}
