package com.example.domain.model


data class DetailMovie(
    val id:Int,
    val adult:Boolean,
    val backdropPath:String,
    val originalTitle:String,
    val posterPath:String,
    val genre:String,
    val director:String,
    val duration:String,
    val releaseDate:String,
    val voteAverage:Double,
    val voteCount:Int,
    val status:String,
    val overview:String,
)
