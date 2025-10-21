package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrAudioModel
import java.time.Instant


interface ZikrAudioRepository {
     fun getAllZikrAudios(): List<ZikrAudioModel>
     fun getZikrAudioById(id: String): ZikrAudioModel?
     fun createZikrAudio(zikrAudio: ZikrAudioModel): Boolean
     fun updateZikrAudio(zikrAudio: ZikrAudioModel): Boolean
     fun deleteZikrAudio(id: String): Boolean

     fun getUpdatedZikrAudios(updatedAt: Instant): List<ZikrAudioModel>
}