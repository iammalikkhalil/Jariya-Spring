package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.IdRequestDto
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrHadithTranslationDtoRequest
import com.example.demo.presentation.service.zikr.ZikrHadithTranslationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api/zikrHadithTranslation")
class ZikrHadithTranslationController(
    private val zikrHadithTranslationService: ZikrHadithTranslationService
) {

    @GetMapping("/getAll")
    fun getAllZikrHadithTranslations(): ResponseEntity<ApiResponse<Any>> {
        val result = zikrHadithTranslationService.getAllZikrHadithTranslations()
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK,
                "Fetched all ZikrHadithTranslation records",
                result
            )
        )
    }

    @PostMapping("/getById")
    fun getZikrHadithTranslationById(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val record = zikrHadithTranslationService.getZikrHadithTranslationById(body.id)

        return if (record != null) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrHadithTranslation found",
                    record
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrHadithTranslation not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrHadithTranslation(@Valid @RequestBody body: ZikrHadithTranslationDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val created = zikrHadithTranslationService.createZikrHadithTranslation(body)

        return if (created) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    ApiResponse.success(
                        HttpStatus.CREATED,
                        "ZikrHadithTranslation created successfully",
                        null
                    )
                )
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    ApiResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to create ZikrHadithTranslation"
                    )
                )
        }
    }

    @PostMapping("/update")
    fun updateZikrHadithTranslation(@Valid @RequestBody body: ZikrHadithTranslationDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val updated = zikrHadithTranslationService.updateZikrHadithTranslation(body)

        return if (updated) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrHadithTranslation updated successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                    ApiResponse.error(
                        HttpStatus.NOT_FOUND,
                        "ZikrHadithTranslation not found or update failed"
                    )
                )
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrHadithTranslation(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val deleted = zikrHadithTranslationService.deleteZikrHadithTranslation(body.id)

        return if (deleted) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrHadithTranslation deleted successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                    ApiResponse.error(
                        HttpStatus.NOT_FOUND,
                        "ZikrHadithTranslation not found or delete failed"
                    )
                )
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@Valid @RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {

            val updatedAt = Instant.parse(body.updatedAt)
            val result = zikrHadithTranslationService.getUpdatedZikrHadithTranslations(updatedAt)

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
