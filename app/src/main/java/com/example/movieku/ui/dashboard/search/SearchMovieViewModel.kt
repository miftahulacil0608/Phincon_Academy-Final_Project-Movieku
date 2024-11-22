package com.example.movieku.ui.dashboard.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.movie.SearchMovie
import com.example.domain.usecase.MovieUseCase
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _searchMovieData = MutableStateFlow<ResultState<SearchMovie>>(ResultState.Loading)
    val searchMovieData = _searchMovieData.asStateFlow()


    fun searchMovie(movieName:String){
        viewModelScope.launch {
            _searchMovieData.value = ResultState.Loading
            try {
                val searchMovieResult = movieUseCase.searchMovie(movieName)
                _searchMovieData.value = ResultState.Success(searchMovieResult)
            }catch (e:Exception){
                _searchMovieData.value = ResultState.Error(e)
            }
        }
    }


}