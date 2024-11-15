package com.example.data.model.dto.network.tmdb


import com.example.data.model.dto.network.tmdb.result.VideoMovieDtoItem
import com.google.gson.annotations.SerializedName

data class VideosMovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val resultsVideos: List<VideoMovieDtoItem>
)