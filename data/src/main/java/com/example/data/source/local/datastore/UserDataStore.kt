package com.example.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class UserDataStore(private val dataStore: DataStore<Preferences>) {
    suspend fun saveUser(userId: String, username: String, email: String) {
        dataStore.edit {
            it[USER_ID_KEY] = userId
            it[USERNAME_KEY] = username
            it[EMAIL_KEY] = email
        }
    }

    fun getUserId() = runBlocking {
        dataStore.data.first()[USER_ID_KEY]
    }

    fun fetchUser(): Flow<UserData> {
        return dataStore.data.map {
            UserData(
                it[USER_ID_KEY] ?: "",
                it[USERNAME_KEY] ?: "",
                it[EMAIL_KEY] ?: "",
            )
        }
    }

    companion object {
        private val USER_ID_KEY = stringPreferencesKey("USER ID KEY")
        private val USERNAME_KEY = stringPreferencesKey("USER ID KEY")
        private val EMAIL_KEY = stringPreferencesKey("USER ID KEY")
    }
}