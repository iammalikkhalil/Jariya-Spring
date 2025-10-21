package com.example.demo.domain.repository.progress

import com.example.demo.domain.model.progress.ZikrProgressModel


interface ZikrProgressRepository {
     fun getAllZikrProgresses(): List<ZikrProgressModel>
     fun getZikrProgressById(id: String): ZikrProgressModel?
     fun createZikrProgress(zikrProgress: ZikrProgressModel): Boolean
     fun updateZikrProgress(zikrProgress: ZikrProgressModel): Boolean
     fun deleteZikrProgress(id: String): Boolean
     fun getUncompletedRecords(): List<ZikrProgressModel>
     fun incrementZikrProgress(id: String, level: Int): Boolean
     fun markZikrProgressAsComplete(id: String): Boolean
}