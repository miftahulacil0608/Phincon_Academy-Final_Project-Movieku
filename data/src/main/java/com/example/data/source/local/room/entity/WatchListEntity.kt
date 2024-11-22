package com.example.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class
WatchListEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    @ColumnInfo("email_user")
    val emailUser:String,
    @ColumnInfo("movie_id")
    val movieId: Int,
    @ColumnInfo("movie_name")
    val movieName: String,
    @ColumnInfo("movie_image")
    val movieImage: String,
    @ColumnInfo("rating_count")
    val ratingCount: Float,
    @ColumnInfo("vote_count")
    val voteCount: Int,
    @ColumnInfo("movie_genre")
    val movieGenre: String,
    @ColumnInfo("movie_duration")
    val duration:String,
    @ColumnInfo("movie_release_date")
    val movieReleaseDate: String,
    @ColumnInfo("pg_age")
    val pgAge: String,
    @ColumnInfo("code_language")
    val codeLanguage: String,
    @ColumnInfo("status")
    val status: String,

    )
