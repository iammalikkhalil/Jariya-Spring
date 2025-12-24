package com.example.demo.presentation.service.progress

import com.example.demo.domain.repository.progress.ZikrPointRepository
import com.example.demo.presentation.dto.progress.ZikrPointDto
import com.example.demo.presentation.dto.progress.ZikrPointRequestDto
import com.example.demo.presentation.dto.progress.toDomain
import org.springframework.stereotype.Service

@Service
class ZikrPointService(
    private val zikrPointRepository: ZikrPointRepository
) {

    fun getAllZikrPoints() = zikrPointRepository.getAllZikrPoints()

    fun getZikrPointById(id: String) = zikrPointRepository.getZikrPointById(id)

    fun createZikrPoint(body: ZikrPointRequestDto): Boolean {
        val dto = ZikrPointDto(
            id = body.id,
            zikrId = body.zikrId,
            userId = body.userId,
            progressId = body.progressId,
            progressType = "zikr",
            points = body.points,
            pointsSourceType = body.pointsSourceType,
            sourceUser = body.sourceUser,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt,
            level = body.level,
        )
        return zikrPointRepository.createZikrPoint(dto.toDomain())
    }

    fun updateZikrPoint(body: ZikrPointRequestDto): Boolean {
        val dto = ZikrPointDto(
            id = body.id,
            zikrId = body.zikrId,
            userId = body.userId,
            points = body.points,
            pointsSourceType = body.pointsSourceType,
            sourceUser = body.sourceUser,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt,
            progressId = body.progressId,
            progressType = body.progressType,
            level = body.level,
        )
        return zikrPointRepository.updateZikrPoint(dto.toDomain())
    }

    fun deleteZikrPoint(id: String) = zikrPointRepository.deleteZikrPoint(id)

    fun getZikrPointSummary(id: String) = zikrPointRepository.getZikrPointsSummary(id)

    fun getZikrPointTotal() = zikrPointRepository.getZikrPointsTotal()

    fun getLeaderboard() = zikrPointRepository.getLeaderboard()

    fun getZikrLeaderboard() = zikrPointRepository.getZikrLeaderboard()
}