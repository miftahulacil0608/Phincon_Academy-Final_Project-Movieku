package com.example.data.utils

import com.example.data.model.dto.DetailMovieDto
import com.example.data.model.dto.NowPlayingMovieDto
import com.example.data.model.dto.PopularMovieDto
import com.example.data.model.dto.UpComingMovieDto
import com.example.data.model.dto.result.ResultMovieDto
import com.example.domain.model.DetailMovie
import com.example.domain.model.Movie
import com.example.domain.model.NowPlayingMovie
import com.example.domain.model.PopularMovie
import com.example.domain.model.UpComingMovie

object MapperToDomainData {
    private fun movieDtoToMovie(resultMovieDto: ResultMovieDto): Movie {
        return Movie(
            id = resultMovieDto.id,
            title = resultMovieDto.originalTitle,
            overview = resultMovieDto.overview,
            releaseDate = resultMovieDto.releaseDate,
            posterPath = "https://image.tmdb.org/t/p/original/${resultMovieDto.posterPath}",
            backdropPath = "https://image.tmdb.org/t/p/original/${resultMovieDto.backdropPath}",
            voteRange = resultMovieDto.voteAverage,
            originalLanguage = resultMovieDto.originalLanguage,
            priceMovie = MovieHelper.calculatePriceMovie(resultMovieDto.releaseDate)
        )
    }

    fun popularMovieDtoToPopularMovie(popularMovieDto: PopularMovieDto): PopularMovie {
        val dataMovie = popularMovieDto.results.map {
            movieDtoToMovie(it)
        }
        return PopularMovie(popularMovieDto.page, dataMovie, popularMovieDto.totalPages, popularMovieDto.totalResults)
    }

     fun nowPlayingMovieDtoToNowPlayingMovie(nowPlayingMovieDto: NowPlayingMovieDto): NowPlayingMovie {
        val dataMovie = nowPlayingMovieDto.results.map {
            movieDtoToMovie(it)
        }
        return NowPlayingMovie(nowPlayingMovieDto.page, dataMovie, nowPlayingMovieDto.totalPages, nowPlayingMovieDto.totalResults)
    }

     fun upComingMovieDtoToUpComingMovie(upComingMovieDto: UpComingMovieDto): UpComingMovie {
        val dataMovie = upComingMovieDto.results.map {
            movieDtoToMovie(it)
        }
        return UpComingMovie(upComingMovieDto.page, dataMovie, upComingMovieDto.totalPages, upComingMovieDto.totalResults)
    }

    fun detailMovieDtoToDetailMovie(detailMovieDto: DetailMovieDto, director:String?): DetailMovie {
        return DetailMovie(
            id = detailMovieDto.id,
            adult = detailMovieDto.adult,
            backdropPath = "https://image.tmdb.org/t/p/original/${detailMovieDto.backdropPath}",
            originalTitle = detailMovieDto.originalTitle,
            posterPath = "https://image.tmdb.org/t/p/original/${detailMovieDto.posterPath}",
            genre = MovieHelper.getGenre(detailMovieDto.genres),
            director = director?:"No Director",
            duration = MovieHelper.calculateDuration(detailMovieDto.runtime),
            releaseDate = detailMovieDto.releaseDate,
            voteAverage = detailMovieDto.voteAverage,
            voteCount = detailMovieDto.voteCount,
            status = detailMovieDto.status,
            overview = detailMovieDto.overview
        )
    }
}