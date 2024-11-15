package com.example.data.model.dto.network.tmdb


import com.example.data.model.dto.network.tmdb.result.CastMovieDtoItem
import com.example.data.model.dto.network.tmdb.result.CrewMovieDtoItem
import com.google.gson.annotations.SerializedName

class CreditsMovieDto(
    @SerializedName("cast")
    val cast: List<CastMovieDtoItem>?,
    @SerializedName("crew")
    val crew: List<CrewMovieDtoItem>?,
    @SerializedName("id")
    val id: Int
)