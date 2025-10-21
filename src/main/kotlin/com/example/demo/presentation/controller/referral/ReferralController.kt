package com.example.demo.presentation.controller.referral

import com.example.demo.domain.model.referral.ReferralResult
import com.example.demo.infrastructure.utils.Log
import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.referral.AddReferralRequestDto
import com.example.demo.presentation.dto.referral.GetReferralTreeRequestDto
import com.example.demo.presentation.service.referral.ReferralService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.system.measureTimeMillis

@RestController
@RequestMapping("/api/referral")
class ReferralController(
    private val referralService: ReferralService
) {

    @PostMapping("/addReferral")
    fun addReferral(@RequestBody body: AddReferralRequestDto): ResponseEntity<ApiResponse<Any>> {
        var response: ResponseEntity<ApiResponse<Any>>? = null

        val totalTime = measureTimeMillis {
            when (val result = referralService.addReferral(body.userId, body.referralCode)) {
                is ReferralResult.Success -> {
                    response = ResponseEntity.ok(
                        ApiResponse.success(
                            HttpStatus.OK,
                            result.message,
                            mapOf("referralCreated" to result.referralPathCreated)
                        )
                    )
                }

                ReferralResult.AlreadyReferred -> {
                    response = ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error(HttpStatus.CONFLICT, result.message))
                }

                ReferralResult.InvalidReferralCode -> {
                    response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(HttpStatus.BAD_REQUEST, result.message))
                }

                ReferralResult.SelfReferralNotAllowed -> {
                    response = ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error(HttpStatus.FORBIDDEN, result.message))
                }

                ReferralResult.InternalError -> {
                    response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, result.message))
                }
            }
        }

        Log.info("🎯 Controller+Service total time: ${totalTime}ms")
        return response!!
    }

    @PostMapping("/getReferralTreeUp")
    fun getReferralTreeUp(@RequestBody body: GetReferralTreeRequestDto): ResponseEntity<ApiResponse<Any>> {
        val tree = referralService.getReferralTreeUp(body.userId)
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK, "Fetched upward referral tree", tree)
        )
    }

    @PostMapping("/getReferralTreeDown")
    fun getReferralTreeDown(@RequestBody body: GetReferralTreeRequestDto): ResponseEntity<ApiResponse<Any>> {
        val tree = referralService.getReferralTreeDown(body.userId)
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK, "Fetched downward referral tree", tree)
        )
    }

    @GetMapping("/getAllReferrals")
    fun getAllReferrals(): ResponseEntity<ApiResponse<Any>> {
        // Placeholder - implement later
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all referrals", ""))
    }
}
