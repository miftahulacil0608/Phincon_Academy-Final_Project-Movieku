package com.example.domain.model.movie

data class Movie(
    val id: Int,
    val title: String,
    val genre:String,
    val releaseDate:String,
    val posterPath: String,
    val voteCount:Int,
    val voteRange: Double,
)