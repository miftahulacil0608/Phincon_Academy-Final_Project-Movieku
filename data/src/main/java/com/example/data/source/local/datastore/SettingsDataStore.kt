package com.example.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.data.model.SettingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val dataStore: DataStore<Preferences>) {
    suspend fun saveOnBoarding(isStatus:Boolean) {
        dataStore.edit {
            it[IS_ON_BOARDING_KEY] = isStatus
        }
    }

    suspend fun saveUserAuthentication(isStatus: Boolean){
        dataStore.edit {
            it[IS_USER_AUTHENTICATION_KEY] = isStatus
        }
    }

    suspend fun clearUserAuthentication(){
        dataStore.edit {
            it.remove(IS_USER_AUTHENTICATION_KEY)
        }
    }

    fun fetchSettings(): Flow<SettingData> {
        return dataStore.data.map {
            SettingData(
                it[IS_ON_BOARDING_KEY] ?: false,
                it[IS_USER_AUTHENTICATION_KEY] ?: false,
            )
        }
    }

    companion object {
        private val IS_ON_BOARDING_KEY = booleanPreferencesKey("IS ON BOARDING KEY")
        private val IS_USER_AUTHENTICATION_KEY = booleanPreferencesKey("IS USER AUTHENTICATION KEY")
        private val IS_DARK_MODE_KEY = booleanPreferencesKey("IS DARK MODE KEY")
    }
}