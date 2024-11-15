package com.example.domain.repository

import com.example.domain.model.DetailMovie
import com.example.domain.model.NowPlayingMovie
import com.example.domain.model.OrderRequestFromUser
import com.example.domain.model.OrderResponseUI
import com.example.domain.model.UpComingMovie

interface MovieRepository {
    suspend fun getNowPlayingMovie():NowPlayingMovie
    suspend fun getUpComingMovie():UpComingMovie
    suspend fun getDetailMovie(movieId:Int):DetailMovie
    suspend fun orderMovie(orderRequestFromUser: OrderRequestFromUser):OrderResponseUI
}