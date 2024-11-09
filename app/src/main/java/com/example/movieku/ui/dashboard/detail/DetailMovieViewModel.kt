package com.example.movieku.ui.dashboard.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DetailMovie
import com.example.domain.usecase.MovieUseCase
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _detailMovieData = MutableStateFlow<ResultState<DetailMovie>>(ResultState.Loading)
    val detailMovieData = _detailMovieData.asStateFlow()

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
}