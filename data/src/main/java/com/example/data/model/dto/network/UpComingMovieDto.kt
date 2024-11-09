package com.example.data.model.dto.network


import com.example.data.model.dto.network.result.ResultDatesMovieDto
import com.example.data.model.dto.network.result.ResultMovieDto
import com.google.gson.annotations.SerializedName

class UpComingMovieDto(
    @SerializedName("dates")
    val dates: ResultDatesMovieDto,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultMovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)