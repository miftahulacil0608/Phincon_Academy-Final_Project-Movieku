package com.example.data.model.dto


import com.example.data.model.dto.result.ResultVideoMovieDto
import com.google.gson.annotations.SerializedName

data class VideosMovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val resultsVideos: List<ResultVideoMovieDto>
)