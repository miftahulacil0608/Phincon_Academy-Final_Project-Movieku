package com.example.movieku.ui.dashboard.movie.upcoming

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
class UpComingMovieInCinemaViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _upcomingMovieInCinema = MutableStateFlow<ResultState<UpComingMovie>>(ResultState.Loading)
    val upcomingMovieInCinema = _upcomingMovieInCinema.asStateFlow()

    init {
        getUpcomingMovieInCinema(HelperDateConvert.releaseDateGte(1))
    }

     fun getUpcomingMovieInCinema(releaseDateGte:String){
        viewModelScope.launch {
            _upcomingMovieInCinema.value = ResultState.Loading
            try {
                val resultUpComing = movieUseCase.getUpComingMovie(releaseDateGte)
                _upcomingMovieInCinema.value = ResultState.Success(resultUpComing)
            }catch (e:Exception){
                _upcomingMovieInCinema.value = ResultState.Error(e)
            }
        }
    }

}