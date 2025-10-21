package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrRewardDtoRequest
import com.example.demo.presentation.service.zikr.ZikrRewardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/zikrReward")
class ZikrRewardController(
    private val zikrRewardService: ZikrRewardService
) {

    @GetMapping("/getAll")
    fun getAllZikrRewards(): ResponseEntity<ApiResponse<Any>> {
        val zikrRewards = zikrRewardService.getAllZikrRewards()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all ZikrReward records", zikrRewards))
    }

    @PostMapping("/getById")
    fun getZikrRewardById(@RequestBody body: ZikrRewardDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val zikrReward = zikrRewardService.getZikrRewardById(body.id)
        return if (zikrReward != null) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrReward found", zikrReward))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrReward not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrReward(@RequestBody body: ZikrRewardDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val result = zikrRewardService.createZikrReward(body)
        return if (result) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "ZikrReward created successfully", body))
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrReward"))
        }
    }

    @PostMapping("/update")
    fun updateZikrReward(@RequestBody body: ZikrRewardDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrRewardService.updateZikrReward(body)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrReward updated successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrReward not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrReward(@RequestBody body: ZikrRewardDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrRewardService.deleteZikrReward(body.id)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrReward deleted successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrReward not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val zikrs = zikrRewardService.getUpdatedZikrRewards(updatedAt)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Zikr found", zikrs))
        } catch (e: DateTimeParseException) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"))
        }
    }
}
