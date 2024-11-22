package com.example.domain.usecase

import com.example.domain.model.user.SettingDataUI
import com.example.domain.repository.UserSettingRepository
import javax.inject.Inject

class UserSettingsUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository) {
    suspend fun saveOnBoarding(isStatus:Boolean){
        userSettingRepository.saveOnBoarding(isStatus)
    }
    suspend fun saveUserAuthentication(isStatus:Boolean){
        userSettingRepository.saveUserAuthentication(isStatus)
    }
    suspend fun getSettingsUser(): SettingDataUI {
        return userSettingRepository.getSettings()
    }
    suspend fun clearUserAuthentication(){
        userSettingRepository.clearUserAuthentication()
    }
}