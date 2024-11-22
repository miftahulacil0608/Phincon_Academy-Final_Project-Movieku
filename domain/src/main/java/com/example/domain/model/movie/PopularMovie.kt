package com.example.domain.model.movie


data class PopularMovie(
    val page:Int,
    val dataMovie: List<Movie>,
    val totalPages:Int,
    val totalResults:Int,
)
