package com.example.data.repositoryImpl

import android.util.Log
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
        )
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<Boolean> {
        return fireBaseRemoteDataSourceRepository.signInWithEmailAndPassword(email, password)
    }

    override suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): Result<Boolean> {
        return fireBaseRemoteDataSourceRepository.signInWithGoogle(credentialResponse)
    }

    override suspend fun setUserDataSignup(fullName: String, email: String) {
        localDataSourceRepository.saveUserDataSignup(fullName, email)
    }

    override suspend fun setUserData(){
        val firebaseUser = fireBaseRemoteDataSourceRepository.fetchFirebaseUser()
        if(firebaseUser!=null){
            localDataSourceRepository.saveUserData(firebaseUser = firebaseUser)
            val image = firebaseUser.photoUrl
        }
    }
    override fun signOut() {
        fireBaseRemoteDataSourceRepository.signOut()
    }
}