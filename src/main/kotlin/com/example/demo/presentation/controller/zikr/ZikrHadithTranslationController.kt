package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrHadithTranslationDtoRequest
import com.example.demo.presentation.service.zikr.ZikrHadithTranslationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/zikrHadithTranslation")
class ZikrHadithTranslationController(
    private val zikrHadithTranslationService: ZikrHadithTranslationService
) {

    @GetMapping("/getAll")
    fun getAllZikrHadithTranslations(): ResponseEntity<ApiResponse<Any>> {
        val translations = zikrHadithTranslationService.getAllZikrHadithTranslations()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all ZikrHadithTranslation records", translations))
    }

    @PostMapping("/getById")
    fun getZikrHadithTranslationById(@RequestBody body: ZikrHadithTranslationDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val translation = zikrHadithTranslationService.getZikrHadithTranslationById(body.id)
        return if (translation != null) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrHadithTranslation found", translation))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrHadithTranslation not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrHadithTranslation(@RequestBody body: ZikrHadithTranslationDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val result = zikrHadithTranslationService.createZikrHadithTranslation(body)
        return if (result) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "ZikrHadithTranslation created successfully", body))
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrHadithTranslation"))
        }
    }

    @PostMapping("/update")
    fun updateZikrHadithTranslation(@RequestBody body: ZikrHadithTranslationDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrHadithTranslationService.updateZikrHadithTranslation(body)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrHadithTranslation updated successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrHadithTranslation not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrHadithTranslation(@RequestBody body: ZikrHadithTranslationDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrHadithTranslationService.deleteZikrHadithTranslation(body.id)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrHadithTranslation deleted successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrHadithTranslation not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val zikrs = zikrHadithTranslationService.getUpdatedZikrHadithTranslations(updatedAt)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Zikr found", zikrs))
        } catch (e: DateTimeParseException) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"))
        }
    }
}
