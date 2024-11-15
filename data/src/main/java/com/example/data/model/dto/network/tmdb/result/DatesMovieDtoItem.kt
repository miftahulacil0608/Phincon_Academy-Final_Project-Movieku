package com.example.data.model.dto.network.tmdb.result

import com.google.gson.annotations.SerializedName

data class DatesMovieDtoItem(
    @SerializedName("maximum")
    val maximum: String,
    @SerializedName("minimum")
    val minimum: String
)
