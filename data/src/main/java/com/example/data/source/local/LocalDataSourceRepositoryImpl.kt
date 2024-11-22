package com.example.data.source.local

import com.example.data.model.SettingData
import com.example.data.source.local.datastore.SettingsDataStore
import com.example.data.source.local.room.WatchListDataBase
import com.example.data.source.local.room.entity.WatchListEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSourceRepositoryImpl @Inject constructor(private val datastore: SettingsDataStore, private val watchListDatabase:WatchListDataBase) :
    LocalDataSourceRepository {
    override suspend fun saveOnBoarding(isStatus: Boolean) {
        datastore.saveOnBoarding(isStatus)
    }

    override suspend fun saveUserAuthentication(isStatus: Boolean) {
        datastore.saveUserAuthentication(isStatus)
    }

    override suspend fun saveUserData(firebaseUser: FirebaseUser) {
        datastore.saveUserData(firebaseUser)
    }

    override suspend fun getSettings(): SettingData {
        return datastore.fetchSettings()
    }

    override suspend fun clearUserAuthentication() {
        datastore.clearUserAuthentication()
    }

    //room

    override suspend fun insertWatchList(watchListEntity: WatchListEntity) {
        watchListDatabase.watchListDao().insertWatchList(watchListEntity)
    }

    override suspend fun getWatchList(
        emailUser: String,
    ): Flow<List<WatchListEntity>> {
        return watchListDatabase.watchListDao().getWatchList(emailUser)
    }

    override suspend fun deleteWatchList(emailUser: String, movieId: Int) {
        watchListDatabase.watchListDao().deleteWatchList(emailUser, movieId)
    }

    override suspend fun isWatchList(emailUser: String, movieId: Int): Boolean {
        return watchListDatabase.watchListDao().dataIsExist(emailUser, movieId)
    }

}