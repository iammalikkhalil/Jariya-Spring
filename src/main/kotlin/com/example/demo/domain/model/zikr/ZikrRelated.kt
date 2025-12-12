package com.example.demo.domain.model.zikr

import com.example.demo.presentation.dto.zikr.ZikrAudioDto
import com.example.demo.presentation.dto.zikr.ZikrCollectionDto
import com.example.demo.presentation.dto.zikr.ZikrCollectionMapDto
import com.example.demo.presentation.dto.zikr.ZikrDto
import com.example.demo.presentation.dto.zikr.ZikrHadithDto
import com.example.demo.presentation.dto.zikr.ZikrHadithTranslationDto
import com.example.demo.presentation.dto.zikr.ZikrQualityDto
import com.example.demo.presentation.dto.zikr.ZikrRewardDto
import com.example.demo.presentation.dto.zikr.ZikrTagDto
import com.example.demo.presentation.dto.zikr.ZikrTagMapDto
import com.example.demo.presentation.dto.zikr.ZikrTranslationDto

fun ZikrModel.toDto() : ZikrDto = ZikrDto(
    id = this.id,
    textAr = this.textAr,
    transliteration = this.transliteration,
    quantityNotes = this.quantityNotes,
    sourceNotes = this.sourceNotes,
    isQuran = this.isQuran,
    isHadith = this.isHadith,
    isVerified = this.isVerified,
    charCount = this.charCount,
    verifiedByName = this.verifiedByName,
    verifiedDate = this.verifiedDate,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt,
    titleEn = this.titleEn,
    titleUr = this.titleUr
)


fun ZikrCollectionModel.toDto() = ZikrCollectionDto(
    id = this.id,
    text = this.text,
    isFeatured = this.isFeatured,
    description = this.description,
    orderIndex = this.orderIndex,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)

fun ZikrAudioModel.toDto()= ZikrAudioDto(
    id = this.id,
    zikrId = this.zikrId,
    audioUrl = this.audioUrl,
    languageCode = this.languageCode,
    duration = this.duration,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)


fun ZikrTranslationModel.toDto() = ZikrTranslationDto(
    id = this.id,
    zikrId = this.zikrId,
    translation = this.translation,
    languageCode = this.languageCode,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)




fun ZikrHadithModel.toDto() = ZikrHadithDto(
    id = this.id,
    zikrId = this.zikrId,
    textAr = this.textAr,
    reference = this.reference,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)



fun ZikrTagModel.toDto() = ZikrTagDto(
    id = this.id,
    text = this.text,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)




fun ZikrTagMapModel.toDto() = ZikrTagMapDto(
    id = this.id,
    zikrId = this.zikrId,
    tagId = this.tagId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)




fun ZikrRewardModel.toDto() = ZikrRewardDto(
    id = this.id,
    zikrId = this.zikrId,
    text = this.text,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)


fun ZikrQualityModel.toDto() = ZikrQualityDto(
    id = this.id,
    zikrId = this.zikrId,
    text = this.text,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)




fun ZikrHadithTranslationModel.toDto() = ZikrHadithTranslationDto(
    id = this.id,
    hadithId = this.hadithId,
    translation = this.translation,
    languageCode = this.languageCode,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)


fun ZikrCollectionMapModel.toDto() = ZikrCollectionMapDto(
    id = this.id,
    zikrId = this.zikrId,
    collectionId = this.collectionId,
    countType = this.countType,
    countValue = this.countValue,
    orderIndex = this.orderIndex,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)




data class BulkInsertResponse(
    val message: String,
    val totalEntries: Int,
    val successfulEntries: Int,
    val failedEntries: Int,
    val successfulDetails: List<SuccessfulDetail> = emptyList()
)

data class SuccessfulDetail(
    val index: Int,
    val id: String
)
