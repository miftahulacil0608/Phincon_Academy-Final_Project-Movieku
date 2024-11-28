package com.example.data.utils

import com.example.data.model.dto.network.tmdb.ImagesMovieDto
import com.example.data.model.dto.network.tmdb.LanguageMovieDto
import com.example.data.model.dto.network.tmdb.result.CastMovieDtoItem
import com.example.data.model.dto.network.tmdb.result.CrewMovieDtoItem
import com.example.data.model.dto.network.tmdb.result.GenreMovieDtoItem
import com.example.data.model.dto.network.tmdb.result.VideoMovieDtoItem
import com.example.domain.model.movie.DirectorOrActorItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object MovieHelper {
    private const val DEFAULT_PRICE: Int = 50000

    fun formatDateUI(releaseDateMovie: String):String{
        val releaseDate = LocalDate.parse(releaseDateMovie, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        val releaseDateMovieUI = formatter.format(releaseDate)
        return releaseDateMovieUI
    }

    private fun yearReleaseMovie(releaseDateMovie: String): Int {
        val releaseDate = LocalDate.parse(releaseDateMovie, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
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

        return "${hours}h ${minutes}m"
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
            /*it.job.contains("director",true)*/
            it.job == "Director"
        }?.map{
            DirectorOrActorItem(it.id, it.name, "https://image.tmdb.org/t/p/original/${it.profilePath}")
        }?: emptyList()
        return director
    }

    fun getActors(listActors:List<CastMovieDtoItem>?):List<DirectorOrActorItem>{
        val actors = listActors?.filter {
            it.order in 1..8
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
            //TODO jika hasilnya Nothing maka dia munculin toast ga ada trailer gitu aja
        }?.firstOrNull()?:"Nothing"
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