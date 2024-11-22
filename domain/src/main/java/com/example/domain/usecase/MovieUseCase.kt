package com.example.domain.usecase

import com.example.domain.model.movie.DetailMovie
import com.example.domain.model.movie.NowPlayingMovie
import com.example.domain.model.movie.SearchMovie
import com.example.domain.model.movie.UpComingMovie
import com.example.domain.model.movie.watchlist.WatchListUI
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend fun getNowPlayingMovie(releaseDateGte:String, releaseDateLte:String): NowPlayingMovie {
        return movieRepository.getNowPlayingMovie(releaseDateGte, releaseDateLte)
    }

    suspend fun getUpComingMovie(releaseDateGte: String): UpComingMovie {
        return movieRepository.getUpComingMovie(releaseDateGte)
    }

    suspend fun searchMovie(movieName:String):SearchMovie{
        return movieRepository.searchMovie(movieName)
    }

    suspend fun getDetailMovie(movieId:Int): DetailMovie {
        return movieRepository.getDetailMovie(movieId)
    }

    suspend fun insertWatchListItem(watchListUI: WatchListUI){
        movieRepository.insertWatchList(watchListUI)
    }

    suspend fun getWatchList():Flow<List<WatchListUI>>{
        return movieRepository.getWatchList()
    }

    suspend fun deleteWatchList(movieId:Int){
        movieRepository.deleteWatchList(movieId)
    }

    suspend fun isWatchListExist(movieId: Int): Boolean{
        return movieRepository.isWatchListExist(movieId)
    }


}