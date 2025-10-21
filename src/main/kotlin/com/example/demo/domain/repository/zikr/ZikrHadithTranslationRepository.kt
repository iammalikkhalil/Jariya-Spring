package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrHadithTranslationModel
import java.time.Instant


interface ZikrHadithTranslationRepository {
     fun getAllZikrHadithTranslations(): List<ZikrHadithTranslationModel>
     fun getZikrHadithTranslationById(id: String): ZikrHadithTranslationModel?
     fun createZikrHadithTranslation(zikrHadithTranslation: ZikrHadithTranslationModel): Boolean
     fun updateZikrHadithTranslation(zikrHadithTranslation: ZikrHadithTranslationModel): Boolean
     fun deleteZikrHadithTranslation(id: String): Boolean

     fun getUpdatedZikrHadithTranslations(updatedAt: Instant): List<ZikrHadithTranslationModel>
}