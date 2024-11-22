package com.example.domain.model.movie.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderMovie(
    val id: Int,
    val title: String,
    val poster: String,
    val price: Int,
    val priceFee:Int,
    val pgAge:String,
    val adultCategory:String,
    val codeLanguage:String,
    val originalLanguage:String,
    val rating:String,
    val genre:String,
    val dateWatch:String,
    val cinema:String,
    val timeWatch:String,
    val studio:String,
    val duration:String

) : Parcelable
