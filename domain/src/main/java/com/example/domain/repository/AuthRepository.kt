package com.example.domain.repository

import androidx.credentials.GetCredentialResponse

interface AuthRepository {
    suspend fun signUpWithEmailAndPassword(fullName:String, email:String, password:String):Result<Boolean>
    suspend fun signInWithEmailAndPassword(email:String, password:String):Result<Boolean>
    suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse):Result<Boolean>
    suspend fun setUserDataSignup(fullName: String, email:String)
    suspend fun setUserData()
    fun signOut()
}