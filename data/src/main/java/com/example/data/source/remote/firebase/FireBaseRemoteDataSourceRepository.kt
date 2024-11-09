package com.example.data.source.remote.firebase

import androidx.credentials.GetCredentialResponse
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FireBaseRemoteDataSourceRepository {
    suspend fun signUpWithEmailAndPassword(fullName:String, email:String, password:String):Result<Boolean>
    suspend fun signInWithEmailAndPassword(email:String, password: String):Result<Boolean>
    suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse):Result<Boolean>
    suspend fun isUserAuthentication(): Flow<Boolean>
     fun fetchFirebaseUser():FirebaseUser?
    fun signOut()
}