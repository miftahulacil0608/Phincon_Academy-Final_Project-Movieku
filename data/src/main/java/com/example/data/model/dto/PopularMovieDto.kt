package com.example.data.model.dto


import com.example.data.model.dto.result.ResultMovieDto
import com.google.gson.annotations.SerializedName

class PopularMovieDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultMovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)