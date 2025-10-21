package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrAudioDtoRequest
import com.example.demo.presentation.service.zikr.ZikrAudioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/zikrAudio")
class ZikrAudioController(
    private val zikrAudioService: ZikrAudioService
) {

    @GetMapping("/getAll")
    fun getAllZikrAudios(): ResponseEntity<ApiResponse<Any>> {
        val zikrAudios = zikrAudioService.getAllZikrAudios()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK,"Fetched all ZikrAudio records", zikrAudios))
    }

    @PostMapping("/getById")
    fun getZikrAudioById(@RequestBody body: ZikrAudioDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val zikrAudio = zikrAudioService.getZikrAudioById(body.id)
        return if (zikrAudio != null) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK,"ZikrAudio found", zikrAudio))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND,"ZikrAudio not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrAudio(@RequestBody body: ZikrAudioDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val result = zikrAudioService.createZikrAudio(body)
        return if (result) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED,"ZikrAudio created successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrAudio"))
        }
    }

    @PostMapping("/update")
    fun updateZikrAudio(@RequestBody body: ZikrAudioDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrAudioService.updateZikrAudio(body)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.NO_CONTENT, "ZikrAudio updated successfully", null))
        } else {
            ResponseEntity.ok(ApiResponse.error(HttpStatus.NOT_FOUND,"ZikrAudio not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrAudio(@RequestBody body: ZikrAudioDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrAudioService.deleteZikrAudio(body.id)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.NO_CONTENT,"ZikrAudio deleted successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND,"ZikrAudio not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val zikrs = zikrAudioService.getUpdatedZikrAudios(updatedAt)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK,"Zikr found", zikrs))
        } catch (e: DateTimeParseException) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST,"Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"))
        }
    }
}
