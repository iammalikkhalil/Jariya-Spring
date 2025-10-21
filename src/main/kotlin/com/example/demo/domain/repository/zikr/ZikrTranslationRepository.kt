package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrTranslationModel
import java.time.Instant


interface ZikrTranslationRepository {
     fun getAllZikrTranslations(): List<ZikrTranslationModel>
     fun getZikrTranslationById(id: String): ZikrTranslationModel?
     fun createZikrTranslation(zikrTranslation: ZikrTranslationModel): Boolean
     fun updateZikrTranslation(zikrTranslation: ZikrTranslationModel): Boolean
     fun deleteZikrTranslation(id: String): Boolean

     fun getUpdatedZikrTranslations(updatedAt: Instant): List<ZikrTranslationModel>


}