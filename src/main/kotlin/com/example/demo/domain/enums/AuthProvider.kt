package com.example.demo.domain.enums

enum class AuthProvider(val value: String) {
    USERNAME_PASSWORD("username_password"),
    GOOGLE("google"),
    FACEBOOK("facebook"),
    GITHUB("github"),
    APPLE("apple"),
    MICROSOFT("microsoft"),
    TWITTER("twitter"),
    LINKEDIN("linkedin"),
    OTHER("other");

    companion object {
        fun fromValue(value: String): AuthProvider =
            entries.find { it.value == value.trim().lowercase() } ?: OTHER
    }
}