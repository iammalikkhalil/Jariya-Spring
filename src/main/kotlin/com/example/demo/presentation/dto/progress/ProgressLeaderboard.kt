package com.example.demo.presentation.dto.progress


import com.example.demo.presentation.dto.auth.UserDto


data class LeaderboardUserDto(
    val index: Int,
    val points: Int,
    val user: UserDto
)


data class LeaderboardDto(
    val total: Int,
    val topTen: List<LeaderboardUserDto>
)
