package com.example.domain.model


data class DetailMovie(
    val id: Int,
    val adult: String,
    val pgAge:String,
    val backdropPath: String,
    val originalTitle: String,
    val posterPath: String,
    val genre: String,
    val duration: String,
    val releaseDate: String,
    val rating: Float,
    val totalVote: Int,
    val status: String,
    val overview: String,
    val priceMovie: Int,
    val priceFee:Int,
    val codeLanguage:String,
    val imageMovie:List<String>,
    val language:String,
    val videoUrl: String,
    val director: List<DirectorOrActorItem>,
    val actors: List<DirectorOrActorItem>
)

data class DirectorOrActorItem(val id: Int, val name: String, val profileUrl: String)

