package com.example.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.source.local.room.dao.WatchListDao
import com.example.data.source.local.room.entity.WatchListEntity

@Database(entities = [WatchListEntity::class], version = 1, exportSchema = false)
abstract class WatchListDataBase:RoomDatabase(){
    abstract fun watchListDao():WatchListDao
}