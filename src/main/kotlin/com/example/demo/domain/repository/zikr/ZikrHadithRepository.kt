package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrHadithModel
import java.time.Instant


interface ZikrHadithRepository {
     fun getAllZikrHadiths(): List<ZikrHadithModel>
     fun getZikrHadithById(id: String): ZikrHadithModel?
     fun createZikrHadith(zikrHadith: ZikrHadithModel): Boolean
     fun updateZikrHadith(zikrHadith: ZikrHadithModel): Boolean
     fun deleteZikrHadith(id: String): Boolean
     fun getUpdatedZikrHadiths(updatedAt: Instant): List<ZikrHadithModel>


}