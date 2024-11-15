package com.example.data.model.dto.network.tmdb


import com.example.data.model.dto.network.tmdb.result.GenreMovieDtoItem
import com.google.gson.annotations.SerializedName

data class GenreMovieDto(
    @SerializedName("genres")
    val genres: List<GenreMovieDtoItem>
)