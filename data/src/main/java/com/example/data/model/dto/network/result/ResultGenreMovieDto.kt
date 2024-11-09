package com.example.data.model.dto.network.result

import com.google.gson.annotations.SerializedName

data class ResultGenreMovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
