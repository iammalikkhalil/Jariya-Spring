package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrEntity
import com.example.demo.domain.model.zikr.ZikrModel
import com.example.demo.presentation.dto.zikr.CsvZikrDto
import java.time.Instant.now
import java.util.UUID

fun ZikrEntity.toModel(): ZikrModel =
    ZikrModel(
        id = id.toString(),
        textAr = textAr,
        transliteration = transliteration,
        titleEn = titleEn,
        titleUr = titleUr,
        quantityNotes = quantityNotes,
        sourceNotes = sourceNotes,
        isQuran = isQuran,
        isHadith = isHadith,
        isVerified = isVerified,
        charCount = charCount,
        verifiedByName = verifiedByName,
        verifiedDate = verifiedDate,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

fun ZikrModel.toEntity(): ZikrEntity =
    ZikrEntity(
        id = UUID.fromString(id),
        textAr = textAr,
        transliteration = transliteration,
        titleEn = titleEn,
        titleUr = titleUr,
        quantityNotes = quantityNotes,
        sourceNotes = sourceNotes,
        isQuran = isQuran,
        isHadith = isHadith,
        isVerified = isVerified,
        charCount = charCount,
        verifiedByName = verifiedByName,
        verifiedDate = verifiedDate,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

fun CsvZikrDto.toZikrEntity(zikrId: UUID): ZikrEntity =
    ZikrEntity(
        id = zikrId,
        textAr = textAr,
        transliteration = transliteration,
        titleEn = titleEn,
        titleUr = titleUr,
        quantityNotes = null,
        sourceNotes = null,
        isQuran = isQuran ?: false,
        isHadith = isHadith ?: false,
        isVerified = false,
        charCount = charCount ?: 0,
        verifiedByName = null,
        verifiedDate = null,
        createdAt = now(),
        updatedAt = now(),
        isDeleted = false,
        deletedAt = null
    )
