package com.example.movieku.ui.dashboard.movie.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.movie.NowPlayingMovie
import com.example.domain.usecase.MovieUseCase
import com.example.movieku.utils.HelperDateConvert
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingMovieInCinemaViewModel @Inject constructor(private val movieUseCase: MovieUseCase):
    ViewModel() {
    private val _nowPlayingMovieInCinema = MutableStateFlow<ResultState<NowPlayingMovie>>(
        ResultState.Loading)
    val nowPlayingMovieInCinema = _nowPlayingMovieInCinema.asStateFlow()


    init {
        getNowPlayingMovieInCinema(HelperDateConvert.releaseDateGte(-30), HelperDateConvert.releaseDateLte())
    }

    fun getNowPlayingMovieInCinema(releaseDateGte: String, releaseDateLte: String){
        viewModelScope.launch {
            _nowPlayingMovieInCinema.value = ResultState.Loading
            try {
                val resultNowPlaying = movieUseCase.getNowPlayingMovie(releaseDateGte,releaseDateLte)
                _nowPlayingMovieInCinema.value = ResultState.Success(resultNowPlaying)
            }catch (e:Exception){
                _nowPlayingMovieInCinema.value = ResultState.Error(e)
            }
        }
    }
    }