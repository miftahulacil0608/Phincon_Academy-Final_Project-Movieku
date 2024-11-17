package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cinema(
    val id: Int,
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

