package com.example.domain.model.movie.watchlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WatchListUI(
    val id: Int = 0,
    val emailUser: String?=null,
    val movieId: Int,
    val movieName: String,
    val movieImage: String,
    val ratingCount: Float,
    val voteCount: Int,
    val movieGenre: String,
    val duration: String,
    val movieReleaseDate: String,
    val pgAge:String,
    val codeLanguage:String,
    val status:String,
) : Parcelable
