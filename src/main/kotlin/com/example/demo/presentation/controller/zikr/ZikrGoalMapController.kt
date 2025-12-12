package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrGoalMapDto
import com.example.demo.presentation.dto.zikr.GetZikrByIdDto
import com.example.demo.presentation.service.zikr.ZikrGoalMapService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api/zikrGoalMap")
class ZikrGoalMapController(
    private val zikrGoalMapService: ZikrGoalMapService
) {

    @GetMapping("/getAll")
    fun getAllZikrGoalMaps(): ResponseEntity<ApiResponse<Any>> {
        val result = zikrGoalMapService.getAllZikrGoalMaps()
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK,
                "Fetched all ZikrGoalMap records",
                result
            )
        )
    }

    @PostMapping("/getById")
    fun getZikrGoalMapById(
        @Valid @RequestBody body: GetZikrByIdDto
    ): ResponseEntity<ApiResponse<Any>> {

        val record = zikrGoalMapService.getZikrGoalMapById(body.id)

        return if (record != null) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrGoalMap found",
                    record
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrGoalMap not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrGoalMap(
        @Valid @RequestBody body: ZikrGoalMapDto
    ): ResponseEntity<ApiResponse<Any>> {

        val created = zikrGoalMapService.createZikrGoalMap(body)

        return if (created) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    ApiResponse.success(
                        HttpStatus.CREATED,
                        "ZikrGoalMap created successfully",
                        null
                    )
                )
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrGoalMap"))
        }
    }

    @PostMapping("/update")
    fun updateZikrGoalMap(
        @Valid @RequestBody body: ZikrGoalMapDto
    ): ResponseEntity<ApiResponse<Any>> {

        val success = zikrGoalMapService.updateZikrGoalMap(body)

        return if (success) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrGoalMap updated successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrGoalMap not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrGoalMap(
        @Valid @RequestBody body: GetZikrByIdDto
    ): ResponseEntity<ApiResponse<Any>> {

        val success = zikrGoalMapService.deleteZikrGoalMap(body.id)

        return if (success) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrGoalMap deleted successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrGoalMap not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(
        @Valid @RequestBody body: TimeDto
    ): ResponseEntity<ApiResponse<Any>> {

        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val result = zikrGoalMapService.getUpdatedZikrGoalMaps(updatedAt)

            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "Updated ZikrGoalMap records fetched",
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
