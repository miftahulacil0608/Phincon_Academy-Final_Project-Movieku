package com.example.data.repositoryImpl

import com.example.data.source.remote.network.NetworkRemoteDataSourceRepository
import com.example.data.utils.MapperToDomainData
import com.example.data.utils.MovieHelper
import com.example.domain.model.DetailMovie
import com.example.domain.model.NowPlayingMovie
import com.example.domain.model.PopularMovie
import com.example.domain.model.UpComingMovie
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val networkRemoteDataSourceRepository: NetworkRemoteDataSourceRepository) :
    MovieRepository {
    override suspend fun getPopularMovie(): PopularMovie {
        return MapperToDomainData.popularMovieDtoToPopularMovie(networkRemoteDataSourceRepository.fetchPopularMovie())
    }

    override suspend fun getNowPlayingMovie(): NowPlayingMovie {
        return MapperToDomainData.nowPlayingMovieDtoToNowPlayingMovie(networkRemoteDataSourceRepository.fetchNowPlayingMovie())
    }

    override suspend fun getUpComingMovie(): UpComingMovie {
        return MapperToDomainData.upComingMovieDtoToUpComingMovie(networkRemoteDataSourceRepository.fetchUpComingMovie())
    }

    override suspend fun getDetailMovie(movieId:Int): DetailMovie {
        val listCrew = networkRemoteDataSourceRepository.fetchCreditsMovie(movieId).crew
        val detailMovieDto = networkRemoteDataSourceRepository.fetchDetailMovie(movieId)
        return MapperToDomainData.detailMovieDtoToDetailMovie(detailMovieDto,MovieHelper.getDirector(listCrew))
    }

    //TODO we need videos ID, needs, credits , and image

}