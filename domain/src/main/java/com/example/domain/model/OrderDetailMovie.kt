package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderDetailMovie(
    val id: Int,
    val title: String,
    val poster: String,
    val price: Int,
    val priceFee:Int,
    val pgAge:String,
    val codeLanguage:String
) : Parcelable
