package com.example.data.repositoryImpl

import com.example.data.source.local.LocalDataSourceRepository
import com.example.data.utils.MapperToDomainData.toSettingDataUI
import com.example.domain.model.user.SettingDataUI
import com.example.domain.repository.UserSettingRepository
import javax.inject.Inject

class UserSettingRepositoryImpl @Inject constructor(
    private val localDataSourceRepository: LocalDataSourceRepository
) : UserSettingRepository {
    override suspend fun saveOnBoarding(isStatus: Boolean) {
        localDataSourceRepository.saveOnBoarding(isStatus)
    }

    override suspend fun saveUserAuthentication(isStatus: Boolean) {
        localDataSourceRepository.saveUserAuthentication(isStatus)
    }

    override suspend fun getSettings(): SettingDataUI {
        return localDataSourceRepository.getSettings().toSettingDataUI()

    }
    override suspend fun clearUserAuthentication() {
        localDataSourceRepository.clearUserAuthentication()
    }

}