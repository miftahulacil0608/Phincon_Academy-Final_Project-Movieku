package com.example.data.repositoryImpl

import androidx.credentials.GetCredentialResponse
import com.example.data.source.local.LocalDataSourceRepository
import com.example.data.source.remote.firebase.FireBaseRemoteDataSourceRepository
import com.example.data.source.remote.network.NetworkRemoteDataSourceRepository
import com.example.data.utils.MapperToDomainData.toSettingDataUI
import com.example.domain.model.SettingDataUI
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val fireBaseRemoteDataSourceRepository: FireBaseRemoteDataSourceRepository) :
    AuthRepository {
    override suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): Result<Boolean> {
        return try {
            fireBaseRemoteDataSourceRepository.signInWithGoogle(credentialResponse)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isUserAuthentication(): Flow<Boolean> {
        return fireBaseRemoteDataSourceRepository.isUserAuthentication()
    }

    override fun signOut() {
        fireBaseRemoteDataSourceRepository.signOut()
    }


}