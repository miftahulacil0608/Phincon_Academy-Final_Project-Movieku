package com.example.data.source.remote.network

import com.example.data.model.dto.network.CreditsMovieDto
import com.example.data.model.dto.network.DetailMovieDto
import com.example.data.model.dto.network.ImagesMovieDto
import com.example.data.model.dto.network.NowPlayingMovieDto
import com.example.data.model.dto.network.PopularMovieDto
import com.example.data.model.dto.network.UpComingMovieDto
import com.example.data.model.dto.network.VideosMovieDto
import javax.inject.Inject

//TODO anterin hasil dari repo ini ke repo domain dan pisahin datanya
class NetworkRemoteDataSourceImpl @Inject constructor(
    private val apiService: TMDBApiService
) : NetworkRemoteDataSourceRepository {

    override suspend fun fetchPopularMovie(): PopularMovieDto {
        return apiService.fetchPopularMovie()
    }

    override suspend fun fetchNowPlayingMovie(): NowPlayingMovieDto {
        return apiService.fetchNowPlayingMovie()
    }

    override suspend fun fetchUpComingMovie(): UpComingMovieDto {
        return apiService.fetchUpComingMovie()
    }

    override suspend fun fetchDetailMovie(movieId: Int): DetailMovieDto {
        return apiService.fetchDetailMovie(movieId)
    }

    override suspend fun fetchMovieVideos(movieId: Int): VideosMovieDto {
        return apiService.fetchMovieVideos(movieId)
    }

    override suspend fun fetchCreditsMovie(movieId: Int): CreditsMovieDto {
        return apiService.fetchCreditsMovie(movieId)
    }

    override suspend fun fetchPhotosMovie(movieId: Int): ImagesMovieDto {
        return apiService.fetchImagesMovie(movieId)
    }
}