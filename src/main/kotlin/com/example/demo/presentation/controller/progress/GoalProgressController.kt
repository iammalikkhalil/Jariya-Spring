package com.example.demo.presentation.controller.progress

import com.example.demo.domain.repository.progress.GoalProgressRepository
import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.progress.GoalProgressRequestDto
import com.example.demo.presentation.service.progress.GoalProgressService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/goalProgress")
class GoalProgressController(
    private val goalProgressRepository: GoalProgressRepository,
    private val goalProgressService: GoalProgressService,
) {

    @GetMapping("/getAll")
    fun getAllGoalProgress(): ResponseEntity<ApiResponse<Any>> {
        val all = goalProgressRepository.getAllGoalProgresses()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all GoalProgress records", all))
    }

    @PostMapping("/getById")
    fun getGoalProgressById(@RequestBody body: GoalProgressRequestDto): ResponseEntity<ApiResponse<Any>> {
        val zikrProgress = goalProgressRepository.getGoalProgressById(body.id)
        return if (zikrProgress != null)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "GoalProgress found", zikrProgress))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "GoalProgress not found"))
    }

    @PostMapping("/add")
    fun createGoalProgress(@RequestBody body: GoalProgressRequestDto): ResponseEntity<ApiResponse<Any>> {
        val created = goalProgressService.createGoalProgress(body)
        return if (created)
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "GoalProgress created successfully", null))
        else
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create GoalProgress"))
    }

}
