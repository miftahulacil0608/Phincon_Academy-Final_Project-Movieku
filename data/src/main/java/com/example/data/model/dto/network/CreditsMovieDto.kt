package com.example.data.model.dto.network


import com.example.data.model.dto.network.result.ResultCastMovieDto
import com.example.data.model.dto.network.result.ResultCrewMovieDto
import com.google.gson.annotations.SerializedName

class CreditsMovieDto(
    @SerializedName("cast")
    val cast: List<ResultCastMovieDto>,
    @SerializedName("crew")
    val crew: List<ResultCrewMovieDto>,
    @SerializedName("id")
    val id: Int
)