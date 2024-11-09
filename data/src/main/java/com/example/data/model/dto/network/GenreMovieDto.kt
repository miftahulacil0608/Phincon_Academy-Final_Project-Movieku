package com.example.data.model.dto.network


import com.example.data.model.dto.network.result.ResultGenreMovieDto
import com.google.gson.annotations.SerializedName

data class GenreMovieDto(
    @SerializedName("genres")
    val genres: List<ResultGenreMovieDto>
)