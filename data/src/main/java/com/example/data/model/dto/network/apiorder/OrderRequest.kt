package com.example.data.model.dto.network.apiorder

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class OrderRequest(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("items")
    val items: List<ItemsRequest>
)

@Parcelize
data class ItemsRequest(
    @SerializedName("date_watch")
    val dateWatch: String,
    @SerializedName("time_watch")
    val timeWatch:String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl:String,
    @SerializedName("genre")
    val genre:String,
    @SerializedName("duration")
    val duration:String,
    @SerializedName("rate_age")
    val pgAge:String,
    @SerializedName("code_language")
    val codeLanguage:String,
    @SerializedName("cinema")
    val cinema:String,
    @SerializedName("studio")
    val studio:String,
    @SerializedName("seat_row")
    val seatRow:String,
    @SerializedName("seat_number")
    val seatNumber:List<Int>,
    @SerializedName("code_ticket")
    val codeTicket:String
):Parcelable
