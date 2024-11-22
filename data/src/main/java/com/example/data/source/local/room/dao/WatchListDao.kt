package com.example.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.source.local.room.entity.WatchListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchListDao {
    @Insert
    suspend fun insertWatchList(watchListEntity: WatchListEntity)

    @Query("SELECT * FROM watchlistentity WHERE email_user LIKE :emailUser")
    fun getWatchList(emailUser: String): Flow<List<WatchListEntity>>

    @Query(
        """
        DELETE FROM watchlistentity 
        WHERE email_user = :emailUser AND movie_id = :movieId
    """
    )
    suspend fun deleteWatchList(emailUser: String, movieId: Int)

    @Query("""SELECT EXISTS(SELECT 1 FROM watchlistentity WHERE email_user = :emailUser AND movie_id = :movieId )""")
    suspend fun dataIsExist(emailUser: String, movieId: Int): Boolean
}