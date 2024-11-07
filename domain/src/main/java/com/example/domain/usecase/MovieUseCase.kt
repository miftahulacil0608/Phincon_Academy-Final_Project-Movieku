package com.example.domain.usecase

import com.example.domain.model.DetailMovie
import com.example.domain.model.NowPlayingMovie
import com.example.domain.model.PopularMovie
import com.example.domain.model.UpComingMovie
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend fun getPopularMovie():PopularMovie{
        return movieRepository.getPopularMovie()
    }

    suspend fun getNowPlayingMovie():NowPlayingMovie{
        return movieRepository.getNowPlayingMovie()
    }

    suspend fun getUpComingMovie():UpComingMovie{
        return movieRepository.getUpComingMovie()
    }

    suspend fun getDetailMovie(movieId:Int):DetailMovie{
        return movieRepository.getDetailMovie(movieId)
    }
}