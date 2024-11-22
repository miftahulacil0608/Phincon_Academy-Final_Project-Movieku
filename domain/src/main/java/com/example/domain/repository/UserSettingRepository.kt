package com.example.domain.repository

import com.example.domain.model.user.SettingDataUI

interface UserSettingRepository {
    suspend fun saveOnBoarding(isStatus:Boolean)
    suspend fun saveUserAuthentication(isStatus: Boolean)
    suspend fun getSettings(): SettingDataUI
    suspend fun clearUserAuthentication()
}