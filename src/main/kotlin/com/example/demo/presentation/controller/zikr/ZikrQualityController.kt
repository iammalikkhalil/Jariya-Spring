package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.IdRequestDto
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrQualityDtoRequest
import com.example.demo.presentation.service.zikr.ZikrQualityService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api/zikrQuality")
class ZikrQualityController(
    private val zikrQualityService: ZikrQualityService
) {

    @GetMapping("/getAll")
    fun getAllZikrQualities(): ResponseEntity<ApiResponse<Any>> {
        val result = zikrQualityService.getAllZikrQualities()
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK,
                "Fetched all ZikrQuality records",
                result
            )
        )
    }

    @PostMapping("/getById")
    fun getZikrQualityById(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val record = zikrQualityService.getZikrQualityById(body.id)

        return if (record != null) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrQuality found",
                    record
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrQuality not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrQuality(@Valid @RequestBody body: ZikrQualityDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val created = zikrQualityService.createZikrQuality(body)

        return if (created) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    ApiResponse.success(
                        HttpStatus.CREATED,
                        "ZikrQuality created successfully",
                        null
                    )
                )
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    ApiResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to create ZikrQuality"
                    )
                )
        }
    }

    @PostMapping("/update")
    fun updateZikrQuality(@Valid @RequestBody body: ZikrQualityDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val updated = zikrQualityService.updateZikrQuality(body)

        return if (updated) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrQuality updated successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrQuality not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrQuality(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val deleted = zikrQualityService.deleteZikrQuality(body.id)

        return if (deleted) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrQuality deleted successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrQuality not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@Valid @RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {

        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val result = zikrQualityService.getUpdatedZikrQualities(updatedAt)

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
