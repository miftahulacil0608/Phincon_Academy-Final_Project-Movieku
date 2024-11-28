package com.example.data.repositoryImpl

import androidx.credentials.GetCredentialResponse
import com.example.data.source.local.LocalDataSourceRepository
import com.example.data.source.remote.firebase.FireBaseRemoteDataSourceRepository
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val fireBaseRemoteDataSourceRepository: FireBaseRemoteDataSourceRepository,
    private val localDataSourceRepository: LocalDataSourceRepository
) :
    AuthRepository {
    override suspend fun signUpWithEmailAndPassword(
        fullName: String,
        email: String,
        password: String
    ): Result<Boolean> {
        return fireBaseRemoteDataSourceRepository.signUpWithEmailAndPassword(
            fullName,
            email,
            password
        ).apply {
            if (this.isSuccess) {
                localDataSourceRepository.saveUserAuthentication(this.getOrDefault(false))
                localDataSourceRepository.saveUserDataSignup(fullName, email)
            }
        }
    }

    override suspend fun sigInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<Boolean> {
        return fireBaseRemoteDataSourceRepository.signInWithEmailAndPassword(email, password)
            .apply {
                if (this.isSuccess) {
                    localDataSourceRepository.saveUserAuthentication(this.getOrDefault(false))
                    setUserIngData()
                }
            }
    }

    override suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): Result<Boolean> {
        return fireBaseRemoteDataSourceRepository.signInWithGoogle(credentialResponse).apply {
            if (this.isSuccess) {
                localDataSourceRepository.saveUserAuthentication(this.getOrDefault(false))
                setUserIngData()
            }
        }
    }

    override suspend fun setUserDataSignup(fullName: String, email: String) {
        localDataSourceRepository.saveUserDataSignup(fullName, email)
    }

    override fun signOut() {
        fireBaseRemoteDataSourceRepository.signOut()
    }

    private suspend fun setUserIngData() {
        val firebaseUser = fireBaseRemoteDataSourceRepository.fetchFirebaseUser()
        if (firebaseUser != null) {
            localDataSourceRepository.saveUserData(firebaseUser = firebaseUser)
        }
    }
}