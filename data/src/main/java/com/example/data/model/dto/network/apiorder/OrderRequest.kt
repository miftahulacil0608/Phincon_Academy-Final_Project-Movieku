package com.example.data.model.dto.network.apiorder

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class OrderRequest(
    val amount: Int = 100000, val email: String,
    val items: List<ItemsRequest>
)

@Parcelize
data class ItemsRequest(
    val id: Int,
    val name: String,
    val price: Int,
    val quantity: Int = 1, /*val date:String, val rating:Double,*/
):Parcelable
