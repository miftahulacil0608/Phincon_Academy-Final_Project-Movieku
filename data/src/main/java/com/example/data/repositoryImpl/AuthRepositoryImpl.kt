package com.example.data.repositoryImpl

import androidx.credentials.GetCredentialResponse
import com.example.data.source.local.LocalDataSourceRepository
import com.example.data.source.remote.firebase.FireBaseRemoteDataSourceRepository
import com.example.data.source.remote.network.NetworkRemoteDataSourceRepository
import com.example.data.utils.MapperToDomainData.toSettingDataUI
import com.example.domain.model.SettingDataUI
import com.example.domain.model.UserData
import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val fireBaseRemoteDataSourceRepository: FireBaseRemoteDataSourceRepository
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

    override suspend fun isUserAuthentication(): Flow<Boolean> {
        return fireBaseRemoteDataSourceRepository.isUserAuthentication()
    }

    //TODO lakukan perubahan biar lebih efektif
    override suspend fun fetchUserSession(): UserData {
        val firebaseUser = fireBaseRemoteDataSourceRepository.fetchFirebaseUser()
        return UserData(
            displayName = firebaseUser?.displayName ?: "",
            email = firebaseUser?.email ?: "",
        )
    }
    override fun signOut() {
        fireBaseRemoteDataSourceRepository.signOut()
    }
}