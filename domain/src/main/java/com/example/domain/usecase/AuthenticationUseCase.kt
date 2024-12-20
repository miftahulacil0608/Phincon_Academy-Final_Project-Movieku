package com.example.domain.usecase

import androidx.credentials.GetCredentialResponse
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend fun signUpWithEmailAndPassword(fullName:String, email:String, password:String):Result<Boolean>{
        return authRepository.signUpWithEmailAndPassword(fullName, email, password)
    }
    suspend fun signInWithGoogle(getCredentialResponse: GetCredentialResponse): Result<Boolean> {
        return authRepository.signInWithGoogle(getCredentialResponse)
    }

    suspend fun signInWithEmailAndPassword2(email: String, password: String):Result<Boolean>{
        return authRepository.sigInWithEmailAndPassword(email, password)
    }

    suspend fun setUserDataSignup(fullName: String, email:String){
        authRepository.setUserDataSignup(fullName, email)
    }

    suspend fun setUserData(){
        authRepository.setUserData()
    }
    fun signOut(){
        authRepository.signOut()
    }
}