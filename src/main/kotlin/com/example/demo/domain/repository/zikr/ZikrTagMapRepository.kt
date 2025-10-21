package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrTagMapModel
import java.time.Instant


interface ZikrTagMapRepository {
     fun getAllZikrTagMaps(): List<ZikrTagMapModel>
     fun getZikrTagMapById(id: String): ZikrTagMapModel?
     fun createZikrTagMap(zikrTagMap: ZikrTagMapModel): Boolean
     fun updateZikrTagMap(zikrTagMap: ZikrTagMapModel): Boolean
     fun deleteZikrTagMap(id: String): Boolean

     fun getUpdatedZikrTagMaps(updatedAt: Instant): List<ZikrTagMapModel>


}