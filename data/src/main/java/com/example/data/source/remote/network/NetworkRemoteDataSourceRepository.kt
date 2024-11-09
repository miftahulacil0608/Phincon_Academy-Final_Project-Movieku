package com.example.data.source.remote.network

import com.example.data.model.dto.CreditsMovieDto
import com.example.data.model.dto.DetailMovieDto
import com.example.data.model.dto.ImagesMovieDto
import com.example.data.model.dto.NowPlayingMovieDto
import com.example.data.model.dto.PopularMovieDto
import com.example.data.model.dto.UpComingMovieDto
import com.example.data.model.dto.VideosMovieDto

interface NetworkRemoteDataSourceRepository {
    suspend fun fetchPopularMovie():PopularMovieDto
    suspend fun fetchNowPlayingMovie():NowPlayingMovieDto
    suspend fun fetchUpComingMovie():UpComingMovieDto
    suspend fun fetchDetailMovie(movieId:Int):DetailMovieDto
    suspend fun fetchMovieVideos(movieId:Int):VideosMovieDto
    suspend fun fetchCreditsMovie(movieId: Int):CreditsMovieDto
    suspend fun fetchPhotosMovie(movieId:Int):ImagesMovieDto
}