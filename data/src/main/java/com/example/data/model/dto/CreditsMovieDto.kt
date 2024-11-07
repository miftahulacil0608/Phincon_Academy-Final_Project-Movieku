package com.example.data.model.dto


import com.example.data.model.dto.result.ResultCastMovieDto
import com.example.data.model.dto.result.ResultCrewMovieDto
import com.google.gson.annotations.SerializedName

class CreditsMovieDto(
    @SerializedName("cast")
    val cast: List<ResultCastMovieDto>,
    @SerializedName("crew")
    val crew: List<ResultCrewMovieDto>,
    @SerializedName("id")
    val id: Int
)