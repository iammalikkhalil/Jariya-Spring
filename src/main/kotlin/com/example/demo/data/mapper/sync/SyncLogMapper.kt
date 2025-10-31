package com.example.demo.data.mapper.sync

import com.example.demo.data.entity.SyncLogEntity
import com.example.demo.domain.model.sync.SyncLogModel

fun SyncLogEntity.toModel(): SyncLogModel =
    SyncLogModel(
        tName = tName,
        updatedAt = updatedAt,
    )

fun SyncLogModel.toEntity(): SyncLogEntity =
    SyncLogEntity(
        tName = tName,
        updatedAt = updatedAt,
    )
