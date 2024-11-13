package com.example.data.model.dto.network


import com.example.data.model.dto.network.result.MovieDtoItem
import com.google.gson.annotations.SerializedName

class PopularMovieDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDtoItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)