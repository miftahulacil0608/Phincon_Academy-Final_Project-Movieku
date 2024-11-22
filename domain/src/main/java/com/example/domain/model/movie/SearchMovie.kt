package com.example.domain.model.movie

import com.example.domain.model.movie.search.SearchMovieItem


data class SearchMovie(
    val page:Int,
    val dataMovie: List<SearchMovieItem>,
    val totalPages:Int,
    val totalResults:Int,
)
