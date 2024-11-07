package com.example.data.repositoryImpl

import com.example.data.source.remote.RemoteDataSourceRepository
import com.example.data.utils.MapperToDomainData
import com.example.data.utils.MovieHelper
import com.example.domain.model.DetailMovie
import com.example.domain.model.NowPlayingMovie
import com.example.domain.model.PopularMovie
import com.example.domain.model.UpComingMovie
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val remoteDataSourceRepository: RemoteDataSourceRepository) :
    MovieRepository {
    override suspend fun getPopularMovie(): PopularMovie {
        return MapperToDomainData.popularMovieDtoToPopularMovie(remoteDataSourceRepository.fetchPopularMovie())
    }

    override suspend fun getNowPlayingMovie(): NowPlayingMovie {
        return MapperToDomainData.nowPlayingMovieDtoToNowPlayingMovie(remoteDataSourceRepository.fetchNowPlayingMovie())
    }

    override suspend fun getUpComingMovie(): UpComingMovie {
        return MapperToDomainData.upComingMovieDtoToUpComingMovie(remoteDataSourceRepository.fetchUpComingMovie())
    }

    override suspend fun getDetailMovie(movieId:Int): DetailMovie {
        val listCrew = remoteDataSourceRepository.fetchCreditsMovie(movieId).crew
        val detailMovieDto = remoteDataSourceRepository.fetchDetailMovie(movieId)
        return MapperToDomainData.detailMovieDtoToDetailMovie(detailMovieDto,MovieHelper.getDirector(listCrew))
    }

    //TODO we need videos ID, needs, credits , and image

}