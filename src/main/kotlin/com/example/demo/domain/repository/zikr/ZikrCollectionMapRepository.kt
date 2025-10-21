package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrCollectionMapModel
import java.time.Instant


interface ZikrCollectionMapRepository {
     fun getAllZikrCollectionMaps(): List<ZikrCollectionMapModel>
     fun getZikrCollectionMapById(id: String): ZikrCollectionMapModel?
     fun createZikrCollectionMap(zikrCollectionMap: ZikrCollectionMapModel): Boolean
     fun updateZikrCollectionMap(zikrCollectionMap: ZikrCollectionMapModel): Boolean
     fun deleteZikrCollectionMap(id: String): Boolean

     fun getUpdatedZikrCollectionMaps(updatedAt: Instant): List<ZikrCollectionMapModel>
}