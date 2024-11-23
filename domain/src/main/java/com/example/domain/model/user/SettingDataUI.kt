package com.example.domain.model.user

data class SettingDataUI(
    val isOnboarding: Boolean,
    val isUserAuthentication: Boolean,
    val email: String,
    val displayName: String
)
