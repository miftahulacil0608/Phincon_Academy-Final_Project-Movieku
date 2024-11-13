package com.example.data.model.dto.network


import com.example.data.model.dto.network.result.VideoMovieDtoItem
import com.google.gson.annotations.SerializedName

data class VideosMovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val resultsVideos: List<VideoMovieDtoItem>
)