package com.example.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo("movie_id")
    val movieId: Int,
    @ColumnInfo("movie_name")
    val movieName: String,
    @ColumnInfo("movie_image")
    val movieImage:String,
    @ColumnInfo("movie_release_date")
    val movieReleaseDate: String,
    @ColumnInfo("movie_vote_average_score")
    val movieVoteAverageScore: String,
    @ColumnInfo("movie_genre")
    val movieGenre:String
)
