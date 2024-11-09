package com.example.domain.repository

import com.example.domain.model.SettingDataUI
import kotlinx.coroutines.flow.Flow

interface UserSettingRepository {
    suspend fun saveOnBoarding(isStatus:Boolean)
    suspend fun saveUserAuthentication(isStatus: Boolean)
    suspend fun getSettings(): Flow<SettingDataUI>
    suspend fun clearUserAuthentication()
}