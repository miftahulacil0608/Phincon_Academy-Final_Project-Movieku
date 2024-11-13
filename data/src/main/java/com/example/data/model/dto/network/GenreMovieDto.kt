package com.example.data.model.dto.network


import com.example.data.model.dto.network.result.GenreMovieDtoItem
import com.google.gson.annotations.SerializedName

data class GenreMovieDto(
    @SerializedName("genres")
    val genres: List<GenreMovieDtoItem>
)