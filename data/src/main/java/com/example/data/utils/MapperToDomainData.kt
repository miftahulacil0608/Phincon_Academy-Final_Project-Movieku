package com.example.data.utils

import com.example.data.model.SettingData
import com.example.data.model.dto.network.apiorder.ItemsRequest
import com.example.data.model.dto.network.apiorder.OrderDto
import com.example.data.model.dto.network.tmdb.CreditsMovieDto
import com.example.data.model.dto.network.tmdb.DetailMovieDto
import com.example.data.model.dto.network.tmdb.ImagesMovieDto
import com.example.data.model.dto.network.tmdb.LanguageMovieDto
import com.example.data.model.dto.network.tmdb.NowPlayingMovieDto
import com.example.data.model.dto.network.tmdb.SearchMovieDto
import com.example.data.model.dto.network.tmdb.UpComingMovieDto
import com.example.data.model.dto.network.tmdb.VideosMovieDto
import com.example.data.model.dto.network.tmdb.result.GenreMovieDtoItem
import com.example.data.model.dto.network.tmdb.result.MovieDtoItem
import com.example.data.source.local.room.entity.WatchListEntity
import com.example.domain.model.movie.DetailMovie
import com.example.domain.model.movie.order.ItemsRequestFromUser
import com.example.domain.model.movie.Movie
import com.example.domain.model.movie.NowPlayingMovie
import com.example.domain.model.movie.SearchMovie
import com.example.domain.model.movie.order.OrderResponseUI
import com.example.domain.model.user.SettingDataUI
import com.example.domain.model.movie.UpComingMovie
import com.example.domain.model.movie.search.SearchMovieItem
import com.example.domain.model.movie.watchlist.WatchListUI

object MapperToDomainData {
    //onboarding convert data
    fun SettingData.toSettingDataUI(): SettingDataUI {
        return SettingDataUI(
            this.isOnBoarding,
            this.isUserAuthentication,
            this.email,
            this.displayName
        )
    }

    //now playing
    fun nowPlayingMovieDtoToNowPlayingMovie(
        nowPlayingMovieDto: NowPlayingMovieDto,
        listGenreMovieDto: List<GenreMovieDtoItem>
    ): NowPlayingMovie {
        val dataMovie = nowPlayingMovieDto.results.map {
            movieDtoToMovie(it, listGenreMovieDto)
        }
        return NowPlayingMovie(
            nowPlayingMovieDto.page,
            dataMovie,
            nowPlayingMovieDto.totalPages,
            nowPlayingMovieDto.totalResults
        )
    }
    //upcoming movie
    fun upComingMovieDtoToUpComingMovie(
        upComingMovieDto: UpComingMovieDto,
        listGenreMovieDto: List<GenreMovieDtoItem>
    ): UpComingMovie {
        val dataMovie = upComingMovieDto.results.map {
            movieDtoToMovie(it, listGenreMovieDto)
        }
        return UpComingMovie(
            upComingMovieDto.page,
            dataMovie,
            upComingMovieDto.totalPages,
            upComingMovieDto.totalResults
        )
    }

    //search movie
    fun searchMovieDtoToSearchMovie(searchMovieDto: SearchMovieDto):SearchMovie{
        val searchMovieItem = searchMovieDto.results.map {
            SearchMovieItem(it.id, it.title,"https://image.tmdb.org/t/p/original/${it.posterPath}")
        }
        return SearchMovie(searchMovieDto.page, searchMovieItem, searchMovieDto.totalPages, searchMovieDto.totalResults)
    }



    //convert movieDto to MovieUI (list movie)
    private fun movieDtoToMovie(
        movieDtoItem: MovieDtoItem,
        listGenreMovieDto: List<GenreMovieDtoItem>
    ): Movie {

        //genre convert using movieHelper
        val genres = MovieHelper.filterGenre(movieDtoItem.genreIds, listGenreMovieDto)
        val resultGenres = MovieHelper.getGenre(genres)

        return Movie(
            id = movieDtoItem.id,
            title = movieDtoItem.originalTitle,
            posterPath = "https://image.tmdb.org/t/p/original/${movieDtoItem.posterPath}",
            genre = resultGenres,
            releaseDate = MovieHelper.formatDateUI(movieDtoItem.releaseDate),
            voteCount = movieDtoItem.voteCount,
            voteRange = movieDtoItem.voteAverage / 2
        )
    }

    //convert detail movieDto to detail movie UI
    fun detailMovieDtoToDetailMovie(
        detailMovieDto: DetailMovieDto,
        credits: CreditsMovieDto,
        videoMovieDto: VideosMovieDto,
        languageDto: List<LanguageMovieDto>,
        imagesMovieDto: ImagesMovieDto
    ): DetailMovie {
        return DetailMovie(
            id = detailMovieDto.id,
            adult = if (detailMovieDto.adult) "Yes" else "No",
            pgAge = if (detailMovieDto.adult) "+17" else "+13",
            backdropPath = "https://image.tmdb.org/t/p/original/${detailMovieDto.backdropPath}",
            originalTitle = detailMovieDto.originalTitle,
            posterPath = "https://image.tmdb.org/t/p/original/${detailMovieDto.posterPath}",
            rating = (detailMovieDto.voteAverage / 2).toFloat(),
            totalVote = detailMovieDto.voteCount,
            //TODO buatkan sebuah logika, jika releaseDate nya +30 day dari hari ini gabisa di buy
            status = detailMovieDto.status,
            overview = detailMovieDto.overview,
            codeLanguage = detailMovieDto.originalLanguage.uppercase(),

            //using movieHelper
            releaseDate = MovieHelper.formatDateUI(detailMovieDto.releaseDate),
            imageMovie = MovieHelper.getImages(imagesMovieDto.backdrops),
            priceMovie = MovieHelper.calculatePriceMovie(detailMovieDto.releaseDate),
            priceFee = MovieHelper.calculatePriceMovie(detailMovieDto.releaseDate) * 10 / 100,
            duration = MovieHelper.calculateDuration(detailMovieDto.runtime),
            genre = MovieHelper.getGenre(detailMovieDto.genres),
            language = MovieHelper.getOriginalLanguage(
                detailMovieDto.originalLanguage,
                languageDto
            ),
            videoUrl = MovieHelper.getVideoTrailer(videoMovieDto.resultsVideos),
            director = MovieHelper.getDirector(listCrew = credits.crew),
            actors = MovieHelper.getActors(listActors = credits.cast),
        )
    }




    fun WatchListUI.toWatchListEntity(emailUser:String): WatchListEntity {
        return WatchListEntity(
            emailUser = emailUser,
            movieId = this.movieId,
            movieName = this.movieName,
            movieImage = this.movieImage,
            ratingCount = this.ratingCount,
            voteCount = this.voteCount,
            movieGenre = this.movieGenre,
            duration = this.duration,
            movieReleaseDate = this.movieReleaseDate,
            pgAge = this.pgAge,
            codeLanguage = this.codeLanguage,
            status= this.status
        )
    }

    fun WatchListEntity.toWatchListEntity(): WatchListUI {
        return WatchListUI(
            id=this.id,
            emailUser = this.emailUser,
            movieId = this.movieId,
            movieName = this.movieName,
            movieImage = this.movieImage,
            ratingCount = this.ratingCount,
            voteCount = this.voteCount,
            movieGenre = this.movieGenre,
            duration = this.duration,
            movieReleaseDate = this.movieReleaseDate,
            pgAge = this.pgAge,
            codeLanguage = this.codeLanguage,
            status = this.status
        )
    }

}