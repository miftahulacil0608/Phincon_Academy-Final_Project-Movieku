package com.example.domain.model.movie.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class OrderRequestFromUser(
    val amount: Int,
    val itemsRequest: List<ItemsRequestFromUser>
)

@Parcelize
data class ItemsRequestFromUser(
    val dateWatch: String,
    val timeWatch: String,
    val id: Int,
    val price: Int,
    val quantity: Int,
    val name: String,
    val imageUrl: String,
    val genre: String,
    val duration:String,
    val pgAge: String,
    val codeLanguage: String,
    val cinema: String,
    val studio: String,
    val seatRow: String,
    val seatNumber: List<Int>,
    val codeTicket: String
) : Parcelable
