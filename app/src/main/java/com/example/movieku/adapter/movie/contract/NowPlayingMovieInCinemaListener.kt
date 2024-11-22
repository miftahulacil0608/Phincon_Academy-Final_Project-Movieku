package com.example.movieku.adapter.movie.contract

import com.example.domain.model.movie.Movie

interface NowPlayingMovieInCinemaListener {
    fun onItemClick(item:Movie)
}