package com.example.data.source.local

import com.example.data.model.SettingData
import kotlinx.coroutines.flow.Flow

interface LocalDataSourceRepository {
    suspend fun saveOnBoarding(isStatus: Boolean)
    suspend fun saveUserAuthentication(isStatus: Boolean)
    suspend fun getSettings(): Flow<SettingData>
    suspend fun clearUserAuthentication()
}