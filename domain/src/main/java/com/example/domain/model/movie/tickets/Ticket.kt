package com.example.domain.model.movie.tickets

data class Ticket(
    val cinema: String,
    val codeLanguage: String,
    val codeTicket: String,
    val dateWatch: String,
    val duration: String,
    val genre: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val rateAge: String,
    val price: Int,
    val quantity: Int,
    val seatNumbers: String,
    val seatRow: String,
    val studio: String,
    val timeWatch: String
)
