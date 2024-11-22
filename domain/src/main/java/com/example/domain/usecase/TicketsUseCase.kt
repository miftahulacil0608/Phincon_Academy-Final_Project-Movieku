package com.example.domain.usecase

import com.example.domain.model.movie.tickets.Ticket
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class TicketsUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun getTickets():List<Ticket>{
        return movieRepository.getTickets()
    }
}