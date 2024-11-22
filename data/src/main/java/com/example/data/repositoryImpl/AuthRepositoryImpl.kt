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

    //TODO lakukan perubahan biar lebih efektif
    override suspend fun setUserData(){
        val firebaseUser = fireBaseRemoteDataSourceRepository.fetchFirebaseUser()
        if(firebaseUser!=null){
            Log.d("emailers", "setUserData: ${firebaseUser.email}")
            localDataSourceRepository.saveUserData(firebaseUser = firebaseUser)
        }
    }
    override fun signOut() {
        fireBaseRemoteDataSourceRepository.signOut()
    }
}