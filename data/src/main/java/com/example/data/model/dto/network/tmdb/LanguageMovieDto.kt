package com.example.data.model.dto.network.tmdb

import com.google.gson.annotations.SerializedName


data class LanguageMovieDto(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("name")
    val name: String
)
