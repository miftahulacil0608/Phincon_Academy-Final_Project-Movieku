package com.example.movieku.adapter.ticket

import com.example.domain.model.movie.tickets.Ticket

interface TicketListener {
    fun onItemClick(item: Ticket)
}