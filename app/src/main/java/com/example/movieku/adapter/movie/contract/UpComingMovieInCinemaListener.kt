package com.example.movieku.adapter.movie.contract

import com.example.domain.model.movie.Movie

interface UpComingMovieInCinemaListener {
    fun onItemClick(item:Movie)
}