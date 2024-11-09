package com.example.domain.usecase

import androidx.credentials.GetCredentialResponse
import com.example.domain.model.DataUser
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun signInWithGoogle(getCredentialResponse: GetCredentialResponse): Result<Boolean> {
        return authRepository.signInWithGoogle(getCredentialResponse)
    }
    suspend fun isUserAuthentication(): Flow<Boolean> {
        return authRepository.isUserAuthentication()
    }
    fun signOut(){
        authRepository.signOut()
    }
}