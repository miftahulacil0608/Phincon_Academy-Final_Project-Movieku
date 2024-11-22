package com.example.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.model.SettingData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class SettingsDataStore(private val dataStore: DataStore<Preferences>) {
    suspend fun saveOnBoarding(isStatus: Boolean) {
        dataStore.edit {
            it[IS_ON_BOARDING_KEY] = isStatus
        }
    }

    suspend fun saveUserData(firebaseUser: FirebaseUser) {
        dataStore.edit {
            it[KEY_EMAIL] = firebaseUser.email ?: ""
            it[KEY_USERNAME] = firebaseUser.displayName ?: ""
        }
    }

    suspend fun saveUserAuthentication(isStatus: Boolean) {
        dataStore.edit {
            it[IS_USER_AUTHENTICATION_KEY] = isStatus
        }
    }

    suspend fun clearUserAuthentication() {
        dataStore.edit {
            it.remove(IS_USER_AUTHENTICATION_KEY)
            it.remove(KEY_EMAIL)
            it.remove(KEY_USERNAME)
        }
    }

    fun fetchSettings(): SettingData = runBlocking{
        dataStore.data.first().let{
            SettingData(
                it[IS_ON_BOARDING_KEY] ?: false,
                it[IS_USER_AUTHENTICATION_KEY] ?: false,
                it[KEY_EMAIL] ?: "",
                it[KEY_USERNAME] ?: ""
            )
        }
    }

    companion object {
        private val IS_ON_BOARDING_KEY = booleanPreferencesKey("IS ON BOARDING KEY")
        private val IS_USER_AUTHENTICATION_KEY = booleanPreferencesKey("IS USER AUTHENTICATION KEY")
        private val KEY_EMAIL = stringPreferencesKey("KEY EMAIL")
        private val KEY_USERNAME = stringPreferencesKey("KEY USERNAME")
        private val IS_DARK_MODE_KEY = booleanPreferencesKey("IS DARK MODE KEY")
    }
}