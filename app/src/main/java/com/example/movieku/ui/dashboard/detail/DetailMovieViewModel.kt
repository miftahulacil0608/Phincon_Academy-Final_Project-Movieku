package com.example.movieku.ui.dashboard.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.movie.DetailMovie
import com.example.domain.model.movie.watchlist.WatchListUI
import com.example.domain.usecase.MovieUseCase
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _detailMovieData = MutableStateFlow<ResultState<DetailMovie>>(ResultState.Loading)
    val detailMovieData = _detailMovieData.asStateFlow()

    private val _isWatchListExist = MutableStateFlow(false)
    val isWatchLisExist = _isWatchListExist.asStateFlow()

    fun getDetailMovie(movieId:Int){
        viewModelScope.launch {
            _detailMovieData.value = ResultState.Loading
            try {
                val fetchDetailMovie = movieUseCase.getDetailMovie(movieId)
                _detailMovieData.value = ResultState.Success(fetchDetailMovie)
            }catch (e:Exception){
                _detailMovieData.value = ResultState.Error(e)
            }
        }
    }

    fun setWatchList(movieId: Int) {
        viewModelScope.launch {
            _isWatchListExist.value = movieUseCase.isWatchListExist(movieId)
        }
    }

    fun toggleFavoriteButton(watchListUI: WatchListUI){
        viewModelScope.launch {
            if (_isWatchListExist.value){
                movieUseCase.deleteWatchList(watchListUI.movieId)
            }else{
                movieUseCase.insertWatchListItem(watchListUI)
            }
            _isWatchListExist.value = movieUseCase.isWatchListExist(watchListUI.movieId)
        }
    }
}