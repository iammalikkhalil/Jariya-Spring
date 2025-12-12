package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.IdRequestDto
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrRewardDtoRequest
import com.example.demo.presentation.service.zikr.ZikrRewardService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api/zikrReward")
class ZikrRewardController(
    private val zikrRewardService: ZikrRewardService
) {

    @GetMapping("/getAll")
    fun getAllZikrRewards(): ResponseEntity<ApiResponse<Any>> {
        val result = zikrRewardService.getAllZikrRewards()
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK,
                "Fetched all ZikrReward records",
                result
            )
        )
    }

    @PostMapping("/getById")
    fun getZikrRewardById(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val record = zikrRewardService.getZikrRewardById(body.id)

        return if (record != null) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrReward found",
                    record
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrReward not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrReward(@Valid @RequestBody body: ZikrRewardDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val created = zikrRewardService.createZikrReward(body)

        return if (created) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    ApiResponse.success(
                        HttpStatus.CREATED,
                        "ZikrReward created successfully",
                        null
                    )
                )
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrReward"))
        }
    }

    @PostMapping("/update")
    fun updateZikrReward(@Valid @RequestBody body: ZikrRewardDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val updated = zikrRewardService.updateZikrReward(body)

        return if (updated) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrReward updated successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                    ApiResponse.error(
                        HttpStatus.NOT_FOUND,
                        "ZikrReward not found or update failed"
                    )
                )
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrReward(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val deleted = zikrRewardService.deleteZikrReward(body.id)

        return if (deleted) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrReward deleted successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrReward not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@Valid @RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {

            val updatedAt = Instant.parse(body.updatedAt)
            val result = zikrRewardService.getUpdatedZikrRewards(updatedAt)

            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "Zikr found",
                    result
                )
            )

        } catch (e: DateTimeParseException) {

            ResponseEntity.badRequest()
                .body(
                    ApiResponse.error(
                        HttpStatus.BAD_REQUEST,
                        "Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"
                    )
                )
        }
    }
}
