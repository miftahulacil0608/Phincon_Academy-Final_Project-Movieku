package com.example.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview:String,
    val releaseDate:String,
    val posterPath: String,
    val backdropPath: String,
    val voteRange: Double,
    val originalLanguage: String,
    val priceMovie:Int
)