package com.example.movieku.ui.dashboard.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.movie.watchlist.WatchListUI
import com.example.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _watchlistMovie = MutableStateFlow<List<WatchListUI>>(emptyList())
    val watchlistMovie = _watchlistMovie.asStateFlow()

    fun getWatchlist(){
        viewModelScope.launch {
            movieUseCase.getWatchList().collect{
                _watchlistMovie.value = it
            }
        }
    }

    fun deleteWatchListMovie(movieId:Int){
        viewModelScope.launch {
            movieUseCase.deleteWatchList(movieId)
        }
    }
}