package com.example.data.source.remote.network

import com.example.data.model.dto.network.CreditsMovieDto
import com.example.data.model.dto.network.DetailMovieDto
import com.example.data.model.dto.network.GenreMovieDto
import com.example.data.model.dto.network.ImagesMovieDto
import com.example.data.model.dto.network.LanguageMovieDto
import com.example.data.model.dto.network.NowPlayingMovieDto
import com.example.data.model.dto.network.UpComingMovieDto
import com.example.data.model.dto.network.VideosMovieDto

interface NetworkRemoteDataSourceRepository {
    suspend fun fetchNowPlayingMovie(): NowPlayingMovieDto
    suspend fun fetchUpComingMovie(): UpComingMovieDto
    suspend fun fetchGenreMovie():GenreMovieDto
    suspend fun fetchLanguageMovie():List<LanguageMovieDto>
    suspend fun fetchDetailMovie(movieId:Int): DetailMovieDto
    suspend fun fetchMovieVideos(movieId:Int): VideosMovieDto
    suspend fun fetchCreditsMovie(movieId: Int): CreditsMovieDto
    suspend fun fetchImagesMovie(movieId:Int): ImagesMovieDto

}