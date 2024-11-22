package com.example.movieku.ui.dashboard.booking

import androidx.lifecycle.ViewModel
import com.example.domain.model.movie.order.OrderRequestFromUser
import com.example.domain.model.movie.order.OrderResponseUI
import com.example.domain.usecase.OrderMovieUseCase
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(private val orderMovieUseCase: OrderMovieUseCase):ViewModel() {
    suspend fun orderMovie(orderMovieFromUser: OrderRequestFromUser): Flow<ResultState<OrderResponseUI>>{
        return flow {
            this.emit(ResultState.Loading)
            try {
                val orderMovie = orderMovieUseCase.orderMovie(orderMovieFromUser)
                this.emit(ResultState.Success(orderMovie))
            }catch (e:Exception){
                this.emit(ResultState.Error(e))
            }
        }

    }
}