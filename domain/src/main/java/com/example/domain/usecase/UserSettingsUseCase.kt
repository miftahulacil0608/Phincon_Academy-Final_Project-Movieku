package com.example.domain.usecase

import com.example.domain.model.SettingDataUI
import com.example.domain.repository.UserSettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserSettingsUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository) {
    suspend fun saveOnBoarding(isStatus:Boolean){
        userSettingRepository.saveOnBoarding(isStatus)
    }
    suspend fun saveUserAuthentication(isStatus:Boolean){
        userSettingRepository.saveUserAuthentication(isStatus)
    }
    suspend fun getSettings(): Flow<SettingDataUI>{
        return userSettingRepository.getSettings()
    }
    suspend fun clearUserAuthentication(){
        userSettingRepository.clearUserAuthentication()
    }
}