package com.example.data.model.dto.network.tmdb


import com.example.data.model.dto.network.tmdb.result.MovieDtoItem
import com.google.gson.annotations.SerializedName

class UpComingMovieDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDtoItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)