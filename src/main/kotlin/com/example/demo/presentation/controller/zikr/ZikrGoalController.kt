package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrGoalDto
import com.example.demo.presentation.dto.zikr.GetZikrByIdDto
import com.example.demo.presentation.service.zikr.ZikrGoalService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException


@RestController
@RequestMapping("/api/zikrGoal")
class ZikrGoalController(
    private val zikrGoalService: ZikrGoalService
) {

    @GetMapping("/getAll")
    fun getAllZikrGoals(): ResponseEntity<ApiResponse<Any>> {
        val result = zikrGoalService.getAllZikrGoals()
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK,
                "Fetched all ZikrGoal records",
                result
            )
        )
    }

    @PostMapping("/getById")
    fun getZikrGoalById(
        @Valid @RequestBody body: GetZikrByIdDto
    ): ResponseEntity<ApiResponse<Any>> {
        val record = zikrGoalService.getZikrGoalById(body.id)

        return if (record != null) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrGoal found",
                    record
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrGoal not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrGoal(
        @Valid @RequestBody body: ZikrGoalDto
    ): ResponseEntity<ApiResponse<Any>> {

        val created = zikrGoalService.createZikrGoal(body)

        return if (created) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    ApiResponse.success(
                        HttpStatus.CREATED,
                        "ZikrGoal created successfully",
                        null
                    )
                )
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrGoal"))
        }
    }

    @PostMapping("/update")
    fun updateZikrGoal(
        @Valid @RequestBody body: ZikrGoalDto
    ): ResponseEntity<ApiResponse<Any>> {

        val success = zikrGoalService.updateZikrGoal(body)

        return if (success) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrGoal updated successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrGoal not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrGoal(
        @Valid @RequestBody body: GetZikrByIdDto
    ): ResponseEntity<ApiResponse<Any>> {

        val success = zikrGoalService.deleteZikrGoal(body.id)

        return if (success) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrGoal deleted successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrGoal not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(
        @Valid @RequestBody body: TimeDto
    ): ResponseEntity<ApiResponse<Any>> {
        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val result = zikrGoalService.getUpdatedZikrGoals(updatedAt)

            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "Updated ZikrGoal records fetched",
                    result
                )
            )
        } catch (e: DateTimeParseException) {
            ResponseEntity.badRequest().body(
                ApiResponse.error(
                    HttpStatus.BAD_REQUEST,
                    "Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"
                )
            )
        }
    }
}
