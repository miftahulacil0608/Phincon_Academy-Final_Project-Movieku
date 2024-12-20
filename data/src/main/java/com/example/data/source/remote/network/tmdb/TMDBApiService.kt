package com.example.data.source.remote.network.tmdb

import com.example.data.model.dto.network.tmdb.CreditsMovieDto
import com.example.data.model.dto.network.tmdb.DetailMovieDto
import com.example.data.model.dto.network.tmdb.GenreMovieDto
import com.example.data.model.dto.network.tmdb.ImagesMovieDto
import com.example.data.model.dto.network.tmdb.LanguageMovieDto
import com.example.data.model.dto.network.tmdb.NowPlayingMovieDto
import com.example.data.model.dto.network.tmdb.SearchMovieDto
import com.example.data.model.dto.network.tmdb.UpComingMovieDto
import com.example.data.model.dto.network.tmdb.VideosMovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {

    @GET("discover/movie")
    suspend fun fetchNowPlayingMovie(
        @Query("primary_release_date.gte")
        releaseDateGte: String,
        @Query("primary_release_date.lte")
        releaseDateLte: String
    ): NowPlayingMovieDto

    @GET("discover/movie")
    suspend fun fetchUpComingMovie(@Query("primary_release_date.gte") releaseDateGte: String): UpComingMovieDto

    @GET("genre/movie/list")
    suspend fun fetchGenre(): GenreMovieDto

    @GET("configuration/languages")
    suspend fun fetchLanguage(): List<LanguageMovieDto>

    @GET("search/movie")
    suspend fun fetchSearchMovie(
        @Query("query") query: String
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