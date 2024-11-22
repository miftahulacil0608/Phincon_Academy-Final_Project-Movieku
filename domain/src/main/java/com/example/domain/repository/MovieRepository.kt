package com.example.domain.repository

import com.example.domain.model.movie.DetailMovie
import com.example.domain.model.movie.NowPlayingMovie
import com.example.domain.model.movie.SearchMovie
import com.example.domain.model.movie.order.OrderRequestFromUser
import com.example.domain.model.movie.order.OrderResponseUI
import com.example.domain.model.movie.UpComingMovie
import com.example.domain.model.movie.tickets.Ticket
import com.example.domain.model.movie.watchlist.WatchListUI
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getNowPlayingMovie(releaseDateGte:String, releaseDateLte:String): NowPlayingMovie
    suspend fun getUpComingMovie(releaseDateGte: String): UpComingMovie
    suspend fun getDetailMovie(movieId:Int): DetailMovie
    suspend fun searchMovie(movieName:String):SearchMovie

    //local db
    suspend fun insertWatchList(watchListUI: WatchListUI)
    suspend fun getWatchList(): Flow<List<WatchListUI>>
    suspend fun deleteWatchList(movieId: Int)
    suspend fun isWatchListExist(movieId: Int):Boolean

    //order movie
    suspend fun orderMovie(orderRequestFromUser: OrderRequestFromUser): OrderResponseUI

    //tickets
    suspend fun getTickets():List<Ticket>

}