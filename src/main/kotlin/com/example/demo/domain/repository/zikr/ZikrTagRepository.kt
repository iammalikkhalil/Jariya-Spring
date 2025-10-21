package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrTagModel
import java.time.Instant


interface ZikrTagRepository {
     fun getAllZikrTags(): List<ZikrTagModel>
     fun getZikrTagById(id: String): ZikrTagModel?
     fun createZikrTag(zikrTag: ZikrTagModel): Boolean
     fun updateZikrTag(zikrTag: ZikrTagModel): Boolean
     fun deleteZikrTag(id: String): Boolean

     fun getUpdatedZikrTags(updatedAt: Instant): List<ZikrTagModel>


}