package com.example.domain.model.movie.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleDataClass(
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
    val duration:String,
):Parcelable
