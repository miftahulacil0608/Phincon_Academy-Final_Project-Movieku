package com.example.data.source.local

import com.example.data.model.SettingData
import com.example.data.source.local.room.entity.WatchListEntity
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface LocalDataSourceRepository {
    suspend fun saveOnBoarding(isStatus: Boolean)
    suspend fun saveUserAuthentication(isStatus: Boolean)
    suspend fun saveUserDataSignup(fullName:String,email:String)
    suspend fun saveUserData(firebaseUser: FirebaseUser)
    suspend fun getSettings(): SettingData
    suspend fun clearUserAuthentication()

    //room database
    suspend fun insertWatchList(watchListEntity: WatchListEntity)
    suspend fun getWatchList(emailUser: String): Flow<List<WatchListEntity>>
    suspend fun deleteWatchList(emailUser: String, movieId: Int)
    suspend fun isWatchList(emailUser: String, movieId: Int):Boolean
}