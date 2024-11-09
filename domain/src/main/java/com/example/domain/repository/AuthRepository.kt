package com.example.domain.repository

import androidx.credentials.GetCredentialResponse
import com.example.domain.model.DataUser
import com.example.domain.model.SettingDataUI
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse):Result<Boolean>
    suspend fun isUserAuthentication(): Flow<Boolean>
    fun signOut()
}