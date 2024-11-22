package com.example.movieku.adapter.search

import com.example.domain.model.movie.Movie
import com.example.domain.model.movie.search.SearchMovieItem

interface SearchMovieListener {
    fun onItemClick(item: SearchMovieItem)
}