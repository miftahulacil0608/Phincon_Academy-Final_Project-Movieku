package com.example.data.model.dto.network.apiorder

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class OrderRequest(
    val amount: Int, val email: String,
    val items: List<ItemsRequest>
)

@Parcelize
data class ItemsRequest(
    val id: Int,
    val name: String,
    val price: Int,
    val quantity: Int,
    val rating:String,
    @SerializedName("image_url")
    val imageUrl:String,
    @SerializedName("genre_movie")
    val genreMovie:String,
    @SerializedName("date_watch")
    val dateWatch:String,
    @SerializedName("cinema")
    val cinema:String,
    @SerializedName("time_watch")
    val timeWatch:String,
    @SerializedName("studio")
    val studio:String,
    /*@SerializedName("number_seat")
    val numberSeat:List<String>*/
):Parcelable
