package com.example.data.model.dto.result

import com.google.gson.annotations.SerializedName

data class ResultGenreMovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
