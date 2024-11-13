package com.example.data.model.dto.network.result

import com.google.gson.annotations.SerializedName

data class DatesMovieDtoItem(
    @SerializedName("maximum")
    val maximum: String,
    @SerializedName("minimum")
    val minimum: String
)
