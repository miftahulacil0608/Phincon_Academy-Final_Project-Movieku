package com.example.domain.model.movie.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchMovieItem(
    val id:Int,
    val title:String,
    val imageUrl:String
):Parcelable
