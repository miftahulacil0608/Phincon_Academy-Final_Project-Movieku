package com.example.data.utils

import com.example.data.model.dto.DetailMovieDto
import com.example.data.model.dto.GenreMovieDto
import com.example.data.model.dto.result.ResultCrewMovieDto
import com.example.data.model.dto.result.ResultGenreMovieDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object MovieHelper {
    private const val DEFAULT_PRICE: Int = 50000

    private fun yearReleaseMovie(releaseDateMovie: String): Int {
        val releaseDate =
            LocalDate.parse(releaseDateMovie, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val formatter = DateTimeFormatter.ofPattern("yyyy")
        val yearReleaseMovie = formatter.format(releaseDate)
        return yearReleaseMovie.toInt()
    }

    fun calculatePriceMovie(releaseDateMovie: String): Int {
        val yearRelease = yearReleaseMovie(releaseDateMovie)
        val currentYear = LocalDate.now().year
        return when (yearRelease) {
            currentYear -> DEFAULT_PRICE
            (currentYear - 1) -> {
                DEFAULT_PRICE / 2
            }

            (currentYear - 2) -> {
                DEFAULT_PRICE / 3
            }

            else -> {
                DEFAULT_PRICE / 4
            }
        }
    }

    //Get Genre
    fun getGenre(listGenre:List<ResultGenreMovieDto>):String{
        val listGenreName = listGenre.map {
            it.name
        }
        return when{
            listGenreName.size >= 2 ->{listGenreName.take(2).joinToString(", ")}
            listGenreName.size == 1 ->{listGenreName.take(1).joinToString(", ")}
            else -> {"No Genre"}
        }
    }

    //CalculateDuration
    fun calculateDuration(runtime: Int): String {
        val hours: Int = runtime / 60
        val minutes: Int = runtime % 60
        val textHours = if (hours >= 1) "s" else ""
        val textMinutes = if (minutes >= 1) "s" else ""

        return "$hours hour$textHours $minutes minute$textMinutes"
    }

    //Get 1 Director
    fun getDirector(listCrew:List<ResultCrewMovieDto>):String?{
        //use firstOrNull because I want get 1 data only
        val director = listCrew.firstOrNull {
            it.job == "Director"
        }?.name

        return director
    }
}