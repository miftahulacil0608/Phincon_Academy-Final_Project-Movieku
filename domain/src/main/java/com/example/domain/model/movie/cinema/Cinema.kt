package com.example.domain.model.movie.cinema

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Cinema(
    val id: Int,
    val date: Date,
    val iconCinema: Int,
    val name: String,
    val address: String,
    val scheduleCinema: List<ScheduleCinema>
) : Parcelable

@Parcelize
data class ScheduleCinema(
    val timeWatch: String,
    val studio: String
) : Parcelable

