package com.example.data.source.remote.firebase

import androidx.credentials.GetCredentialResponse
import kotlinx.coroutines.flow.Flow

interface FireBaseRemoteDataSourceRepository {
    suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse):Result<Boolean>
    suspend fun isUserAuthentication(): Flow<Boolean>
    fun signOut()
}