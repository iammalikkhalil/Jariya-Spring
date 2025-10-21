package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrQualityModel
import java.time.Instant

interface ZikrQualityRepository {
     fun getAllZikrQualities(): List<ZikrQualityModel>
     fun getZikrQualityById(id: String): ZikrQualityModel?
     fun createZikrQuality(zikrQuality: ZikrQualityModel): Boolean
     fun updateZikrQuality(zikrQuality: ZikrQualityModel): Boolean
     fun deleteZikrQuality(id: String): Boolean

     fun getUpdatedZikrQualities(updatedAt: Instant): List<ZikrQualityModel>
}