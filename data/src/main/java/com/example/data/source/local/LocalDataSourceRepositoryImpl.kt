package com.example.data.source.local

import com.example.data.model.SettingData
import com.example.data.source.local.datastore.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSourceRepositoryImpl @Inject constructor(private val datastore: SettingsDataStore) :
    LocalDataSourceRepository {
    override suspend fun saveOnBoarding(isStatus: Boolean) {
        datastore.saveOnBoarding(isStatus)
    }

    override suspend fun saveUserAuthentication(isStatus: Boolean) {
        datastore.saveUserAuthentication(isStatus)
    }

    override suspend fun getSettings(): Flow<SettingData> {
        return datastore.fetchSettings()
    }

    override suspend fun clearUserAuthentication() {
        datastore.clearUserAuthentication()
    }

}