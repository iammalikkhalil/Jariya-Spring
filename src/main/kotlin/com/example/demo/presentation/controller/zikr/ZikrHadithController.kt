package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.IdRequestDto
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrHadithDtoRequest
import com.example.demo.presentation.service.zikr.ZikrHadithService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api/zikrHadith")
class ZikrHadithController(
    private val zikrHadithService: ZikrHadithService
) {

    @GetMapping("/getAll")
    fun getAllZikrHadiths(): ResponseEntity<ApiResponse<Any>> {
        val result = zikrHadithService.getAllZikrHadiths()
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK,
                "Fetched all ZikrHadith records",
                result
            )
        )
    }

    @PostMapping("/getById")
    fun getZikrHadithById(@Valid @RequestBody body: IdRequestDto): ResponseEntity<ApiResponse<Any>> {

        val result = zikrHadithService.getZikrHadithById(body.id)

        return if (result != null) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrHadith found",
                    result
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrHadith not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrHadith(@Valid @RequestBody body: ZikrHadithDtoRequest): ResponseEntity<ApiResponse<Any>> {

        val created = zikrHadithService.createZikrHadith(body)

        return if (created) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    ApiResponse.success(
                        HttpStatus.CREATED,
                        "ZikrHadith created successfully",
                        null
                    )
                )
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrHadith"))
        }
    }

    @PostMapping("/update")
    fun updateZikrHadith(@Valid @RequestBody body: ZikrHadithDtoRequest): ResponseEntity<ApiResponse<Any>> {

        val updated = zikrHadithService.updateZikrHadith(body)

        return if (updated) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrHadith updated successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrHadith not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrHadith(@Valid @RequestBody body: IdRequestDto): ResponseEntity<ApiResponse<Any>> {

        val deleted = zikrHadithService.deleteZikrHadith(body.id)

        return if (deleted) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrHadith deleted successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrHadith not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@Valid @RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val result = zikrHadithService.getUpdatedZikrHadiths(updatedAt)

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
