package com.example.movieku.ui.dashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.NowPlayingMovie
import com.example.domain.model.PopularMovie
import com.example.domain.model.UpComingMovie
import com.example.domain.usecase.MovieUseCase
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _popularMovieData = MutableStateFlow<ResultState<PopularMovie>>(ResultState.Loading)
    val popularMovie = _popularMovieData.asStateFlow()

    private val _nowPlayingMovie = MutableStateFlow<ResultState<NowPlayingMovie>>(ResultState.Loading)
    val nowPlayingMovie = _nowPlayingMovie.asStateFlow()

    private val _upComingMovie = MutableStateFlow<ResultState<UpComingMovie>>(ResultState.Loading)
    val upComingMovie = _upComingMovie.asStateFlow()

    private val _currentItem = MutableLiveData<Int>()
    val currentItem:LiveData<Int> = _currentItem

    fun setCurrentItem(item:Int){
        _currentItem.value = item
    }

    fun getPopularMovie() {
        viewModelScope.launch {
            _popularMovieData.value = ResultState.Loading
            try {
                _popularMovieData.value = ResultState.Success(movieUseCase.getPopularMovie())
            } catch (e: Exception) {
                _popularMovieData.value = ResultState.Error(e)
            }
        }
    }

    fun getNowPlayingMovie() {
        viewModelScope.launch {
            _nowPlayingMovie.value = ResultState.Loading
            try {
                _nowPlayingMovie.value = ResultState.Success(movieUseCase.getNowPlayingMovie())
            } catch (e: Exception) {
                _nowPlayingMovie.value = ResultState.Error(e)
            }
        }
    }

    fun getUpComingMovie() {
        viewModelScope.launch {
            _upComingMovie.value = ResultState.Loading
            try {
                _upComingMovie.value = ResultState.Success(movieUseCase.getUpComingMovie())
            } catch (e: Exception) {
                _upComingMovie.value = ResultState.Error(e)
            }
        }
    }
}