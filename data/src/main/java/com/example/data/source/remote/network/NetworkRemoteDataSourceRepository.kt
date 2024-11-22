package com.example.data.source.remote.network

import com.example.data.model.dto.network.apiorder.OrderDto
import com.example.data.model.dto.network.apiorder.OrderRequest
import com.example.data.model.dto.network.apiorder.OrderTicketsDto
import com.example.data.model.dto.network.tmdb.CreditsMovieDto
import com.example.data.model.dto.network.tmdb.DetailMovieDto
import com.example.data.model.dto.network.tmdb.GenreMovieDto
import com.example.data.model.dto.network.tmdb.ImagesMovieDto
import com.example.data.model.dto.network.tmdb.LanguageMovieDto
import com.example.data.model.dto.network.tmdb.NowPlayingMovieDto
import com.example.data.model.dto.network.tmdb.SearchMovieDto
import com.example.data.model.dto.network.tmdb.UpComingMovieDto
import com.example.data.model.dto.network.tmdb.VideosMovieDto

interface NetworkRemoteDataSourceRepository {
    //fetch major data
    suspend fun fetchNowPlayingMovie(releaseDateGte:String, releaseDateLte:String): NowPlayingMovieDto
    suspend fun fetchUpComingMovie(releaseDateGte: String): UpComingMovieDto
    suspend fun fetchSearchMovie(movieName:String):SearchMovieDto

    //fetch minor attribute data
    suspend fun fetchGenreMovie(): GenreMovieDto
    suspend fun fetchLanguageMovie():List<LanguageMovieDto>
    suspend fun fetchDetailMovie(movieId:Int): DetailMovieDto
    suspend fun fetchMovieVideos(movieId:Int): VideosMovieDto
    suspend fun fetchCreditsMovie(movieId: Int): CreditsMovieDto
    suspend fun fetchImagesMovie(movieId:Int): ImagesMovieDto

    suspend fun postOrderMovie(orderRequest: OrderRequest):OrderDto

    //Fetch order tickets
    suspend fun fetchOrdersTicketsPaid(searchByEmail:String):OrderTicketsDto

}