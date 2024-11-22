package com.example.movieku.ui.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.movie.NowPlayingMovie
import com.example.domain.model.movie.UpComingMovie
import com.example.domain.usecase.MovieUseCase
import com.example.movieku.utils.HelperDateConvert
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _nowPlayingMovie = MutableStateFlow<ResultState<NowPlayingMovie>>(ResultState.Loading)
    val nowPlayingMovie = _nowPlayingMovie.asStateFlow()

    private val _upComingMovie = MutableStateFlow<ResultState<UpComingMovie>>(ResultState.Loading)
    val upComingMovie = _upComingMovie.asStateFlow()

    init {
        getNowPlayingMovie(HelperDateConvert.releaseDateGte(-30), HelperDateConvert.releaseDateLte())
        getUpComingMovie(HelperDateConvert.releaseDateGte(1))
    }

    fun getNowPlayingMovie(releaseDateGte:String, releaseDateLte:String) {
        viewModelScope.launch {
            _nowPlayingMovie.value = ResultState.Loading
            try {
                _nowPlayingMovie.value = ResultState.Success(movieUseCase.getNowPlayingMovie(releaseDateGte, releaseDateLte))
            } catch (e: Exception) {
                _nowPlayingMovie.value = ResultState.Error(e)
            }
        }
    }

    fun getUpComingMovie(releaseDateGte: String) {
        viewModelScope.launch {
            _upComingMovie.value = ResultState.Loading
            try {
                _upComingMovie.value = ResultState.Success(movieUseCase.getUpComingMovie(releaseDateGte))
            } catch (e: Exception) {
                _upComingMovie.value = ResultState.Error(e)
            }
        }
    }
}