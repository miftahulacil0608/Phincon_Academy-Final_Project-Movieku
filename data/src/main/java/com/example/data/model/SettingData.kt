package com.example.data.model

data class SettingData(
    val isOnBoarding: Boolean = false,
    val isUserAuthentication: Boolean = false,
    val email: String = "",
    val displayName: String = "",
)
