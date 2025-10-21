package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrCollectionModel
import java.time.Instant


interface ZikrCollectionRepository {
     fun getAllZikrCollections(): List<ZikrCollectionModel>
     fun getZikrCollectionById(id: String): ZikrCollectionModel?
     fun createZikrCollection(zikrCollection: ZikrCollectionModel): Boolean
     fun updateZikrCollection(zikrCollection: ZikrCollectionModel): Boolean
     fun deleteZikrCollection(id: String): Boolean

     fun getUpdatedZikrCollections(updatedAt: Instant): List<ZikrCollectionModel>
}