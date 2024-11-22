package com.example.domain.usecase

import com.example.domain.model.movie.order.OrderRequestFromUser
import com.example.domain.model.movie.order.OrderResponseUI
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class OrderMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun orderMovie(orderRequestFromUser: OrderRequestFromUser): OrderResponseUI {
        return movieRepository.orderMovie(orderRequestFromUser)
    }
}