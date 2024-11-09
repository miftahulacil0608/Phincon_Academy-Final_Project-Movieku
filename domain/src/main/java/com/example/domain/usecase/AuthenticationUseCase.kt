package com.example.domain.usecase

import androidx.credentials.GetCredentialResponse
import com.example.domain.model.UserData
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend fun signInWithEmailAndPassword(email: String, password: String):Result<Boolean>{
        return authRepository.signInWithEmailAndPassword(email, password)
    }
    suspend fun signUpWithEmailAndPassword(fullName:String, email:String, password:String):Result<Boolean>{
        return authRepository.signUpWithEmailAndPassword(fullName, email, password)
    }
    suspend fun signInWithGoogle(getCredentialResponse: GetCredentialResponse): Result<Boolean> {
        return authRepository.signInWithGoogle(getCredentialResponse)
    }
    suspend fun isUserAuthentication(): Flow<Boolean> {
        return authRepository.isUserAuthentication()
    }

    suspend fun getUserSession():UserData{
        return authRepository.fetchUserSession()
    }

    fun signOut(){
        authRepository.signOut()
    }
}