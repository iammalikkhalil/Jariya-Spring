package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrModel
import com.example.demo.presentation.dto.zikr.CsvZikrDto
import java.time.Instant
import java.util.UUID

interface ZikrRepository {
     fun getAllZikrs(): List<ZikrModel>
     fun getZikrById(id: String): ZikrModel?
     fun createZikr(zikr: ZikrModel): Boolean
     fun updateZikr(zikr: ZikrModel): Boolean
     fun deleteZikr(id: String): Boolean

     fun getUpdatedZikrs(updatedAt: Instant): List<ZikrModel>

     fun bulkInsertZikrs(rows:  List<CsvZikrDto>):  List<Pair<Int, UUID>>
}