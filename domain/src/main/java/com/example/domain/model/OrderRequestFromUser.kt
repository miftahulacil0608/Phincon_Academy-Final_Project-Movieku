package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class OrderRequestFromUser(
    val amount: Int = 100000, val email: String,
    val itemsRequest: List<ItemsRequestFromUser>
)

@Parcelize
data class ItemsRequestFromUser(
    val id: Int,
    val name: String,
    val price: Int,
    val quantity: Int = 1, /*val date:String, val rating:Double,*/
):Parcelable
