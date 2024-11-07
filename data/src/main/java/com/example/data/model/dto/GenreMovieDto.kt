package com.example.data.model.dto


import com.example.data.model.dto.result.ResultGenreMovieDto
import com.google.gson.annotations.SerializedName

data class GenreMovieDto(
    @SerializedName("genres")
    val genres: List<ResultGenreMovieDto>
)