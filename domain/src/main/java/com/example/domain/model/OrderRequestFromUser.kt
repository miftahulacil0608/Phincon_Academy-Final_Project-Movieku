package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class OrderRequestFromUser(
    val amount: Int, val email: String,
    val itemsRequest: List<ItemsRequestFromUser>
)

@Parcelize
data class ItemsRequestFromUser(
    val id: Int,
    val name: String,
    val price: Int,
    val quantity: Int,
    val rating:String,
    val imageUrl:String,
    val genreMovie:String,
    val dateWatch:String,
    val cinema:String,
    val timeWatch:String,
    val studio:String,
    //TODO generate random numberseat
    /*val numberSeat:List<String>*/
    ):Parcelable
