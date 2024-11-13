package com.example.data.utils

import com.example.data.model.dto.network.ImagesMovieDto
import com.example.data.model.dto.network.LanguageMovieDto
import com.example.data.model.dto.network.result.CastMovieDtoItem
import com.example.data.model.dto.network.result.CrewMovieDtoItem
import com.example.data.model.dto.network.result.GenreMovieDtoItem
import com.example.data.model.dto.network.result.VideoMovieDtoItem
import com.example.domain.model.DirectorOrActorItem
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

    //filterGenre for NowPlaying
    fun filterGenre(listGenreId:List<Int>, listGenre:List<GenreMovieDtoItem>):List<GenreMovieDtoItem>{
        val result = mutableListOf<GenreMovieDtoItem>()
        for (genre in listGenre){
            for (genreId in listGenreId){
                if (genreId == genre.id){
                    result.add(genre)
                    break
                }
            }
        }
        return result
    }

    //Get Genre
    fun getGenre(listGenre:List<GenreMovieDtoItem>):String{
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

    fun getImages(listImages:List<ImagesMovieDto.Backdrop>?):List<String>{
        return listImages?.filter {
            it.width < 2160
        }?.map {
          "https://image.tmdb.org/t/p/original/${it.filePath}"
        } ?: emptyList()
    }

    //Get list Director
    fun getDirector(listCrew:List<CrewMovieDtoItem>?):List<DirectorOrActorItem>{
        //use filter to filter item from resultCewMovieDto to DirectorOrActorItem
        val director = listCrew?.filter {
            it.job.contains("director",true)
        }?.map{
            DirectorOrActorItem(it.id, it.name, "https://image.tmdb.org/t/p/original/${it.profilePath}")
        }?: emptyList()
        return director
    }

    fun getActors(listActors:List<CastMovieDtoItem>?):List<DirectorOrActorItem>{
        val actors = listActors?.filter {
            it.order in 1..5
        }?.map {
            DirectorOrActorItem(it.id, it.name, "https://image.tmdb.org/t/p/original/${it.profilePath}")
        }?: emptyList()
        return actors
    }

    fun getVideoTrailer(listVideos:List<VideoMovieDtoItem>?):String{
        val videoTrailer = listVideos?.filter {
            it.official && it.type.contains("Trailer",true)
        }?.map{
            it.key
        }?.first()?:"nothing"
        return videoTrailer
    }

    fun getOriginalLanguage(isoLanguage:String,listLanguageMovieDto: List<LanguageMovieDto>):String{
        val originalLanguage = listLanguageMovieDto.filter {
            it.iso6391.contains(isoLanguage, true)
        }.map {
            it.englishName
        }.first()

        return originalLanguage
    }




}