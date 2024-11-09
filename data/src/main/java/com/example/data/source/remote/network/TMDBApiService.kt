package com.example.data.source.remote.network

import com.example.data.model.dto.network.CreditsMovieDto
import com.example.data.model.dto.network.DetailMovieDto
import com.example.data.model.dto.network.ImagesMovieDto
import com.example.data.model.dto.network.NowPlayingMovieDto
import com.example.data.model.dto.network.PopularMovieDto
import com.example.data.model.dto.network.SearchMovieDto
import com.example.data.model.dto.network.UpComingMovieDto
import com.example.data.model.dto.network.VideosMovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {
    @GET("movie/popular")
    suspend fun fetchPopularMovie(): PopularMovieDto

    @GET("movie/now_playing")
    suspend fun fetchNowPlayingMovie(): NowPlayingMovieDto

    @GET("movie/upcoming")
    suspend fun fetchUpComingMovie(): UpComingMovieDto

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = true
    ): SearchMovieDto

    @GET("movie/{movie_id}")
    suspend fun fetchDetailMovie(@Path("movie_id") movieId: Int): DetailMovieDto

    @GET("movie/{movie_id}/videos")
    suspend fun fetchMovieVideos(@Path("movie_id") movieId: Int): VideosMovieDto

    @GET("movie/{movie_id}/credits")
    suspend fun fetchCreditsMovie(@Path("movie_id") movieId: Int): CreditsMovieDto

    @GET("movie/{movie_id}/images")
    suspend fun fetchImagesMovie(@Path("movie_id") movieId: Int): ImagesMovieDto



}