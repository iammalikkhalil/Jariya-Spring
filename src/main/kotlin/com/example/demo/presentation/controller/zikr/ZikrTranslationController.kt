package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.IdRequestDto
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrTranslationDtoRequest
import com.example.demo.presentation.service.zikr.ZikrTranslationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api/zikrTranslation")
class ZikrTranslationController(
    private val zikrTranslationService: ZikrTranslationService
) {

    @GetMapping("/getAll")
    fun getAllZikrTranslations(): ResponseEntity<ApiResponse<Any>> {
        val result = zikrTranslationService.getAllZikrTranslations()
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK,
                "Fetched all ZikrTranslation records",
                result
            )
        )
    }

    @PostMapping("/getById")
    fun getZikrTranslationById(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val record = zikrTranslationService.getZikrTranslationById(body.id)

        return if (record != null) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrTranslation found",
                    record
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTranslation not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrTranslation(@Valid @RequestBody body: ZikrTranslationDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val created = zikrTranslationService.createZikrTranslation(body)

        return if (created) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    ApiResponse.success(
                        HttpStatus.CREATED,
                        "ZikrTranslation created successfully",
                        null
                    )
                )
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrTranslation"))
        }
    }

    @PostMapping("/update")
    fun updateZikrTranslation(@Valid @RequestBody body: ZikrTranslationDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val updated = zikrTranslationService.updateZikrTranslation(body)

        return if (updated) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrTranslation updated successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                    ApiResponse.error(
                        HttpStatus.NOT_FOUND,
                        "ZikrTranslation not found or update failed"
                    )
                )
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrTranslation(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val deleted = zikrTranslationService.deleteZikrTranslation(body.id)

        return if (deleted) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrTranslation deleted successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTranslation not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@Valid @RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {

        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val result = zikrTranslationService.getUpdatedZikrTranslations(updatedAt)

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
