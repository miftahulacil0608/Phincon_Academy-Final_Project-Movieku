package com.example.domain.repository

import androidx.credentials.GetCredentialResponse
import com.example.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUpWithEmailAndPassword(fullName:String, email:String, password:String):Result<Boolean>
    suspend fun signInWithEmailAndPassword(email:String, password:String):Result<Boolean>
    suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse):Result<Boolean>
    suspend fun isUserAuthentication(): Flow<Boolean>
    suspend fun fetchUserSession():UserData
    fun signOut()
}