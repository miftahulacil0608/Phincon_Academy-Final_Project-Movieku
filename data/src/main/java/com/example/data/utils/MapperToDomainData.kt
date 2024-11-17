package com.example.data.utils

import com.example.data.model.SettingData
import com.example.data.model.dto.network.apiorder.ItemsRequest
import com.example.data.model.dto.network.apiorder.OrderDto
import com.example.data.model.dto.network.tmdb.CreditsMovieDto
import com.example.data.model.dto.network.tmdb.DetailMovieDto
import com.example.data.model.dto.network.tmdb.ImagesMovieDto
import com.example.data.model.dto.network.tmdb.LanguageMovieDto
import com.example.data.model.dto.network.tmdb.NowPlayingMovieDto
import com.example.data.model.dto.network.tmdb.UpComingMovieDto
import com.example.data.model.dto.network.tmdb.VideosMovieDto
import com.example.data.model.dto.network.tmdb.result.GenreMovieDtoItem
import com.example.data.model.dto.network.tmdb.result.MovieDtoItem
import com.example.domain.model.DetailMovie
import com.example.domain.model.ItemsRequestFromUser
import com.example.domain.model.Movie
import com.example.domain.model.NowPlayingMovie
import com.example.domain.model.OrderResponseUI
import com.example.domain.model.SettingDataUI
import com.example.domain.model.UpComingMovie

object MapperToDomainData {
    //onboarding convert data
    fun SettingData.toSettingDataUI(): SettingDataUI {
        return SettingDataUI(this.isOnBoarding, this.isUserAuthentication)
    }

    //now playing
     fun nowPlayingMovieDtoToNowPlayingMovie(nowPlayingMovieDto: NowPlayingMovieDto, listGenreMovieDto: List<GenreMovieDtoItem>): NowPlayingMovie {
        val dataMovie = nowPlayingMovieDto.results.map {
            movieDtoToMovie(it, listGenreMovieDto)
        }
        return NowPlayingMovie(nowPlayingMovieDto.page, dataMovie, nowPlayingMovieDto.totalPages, nowPlayingMovieDto.totalResults)
    }

    //upcoming movie
     fun upComingMovieDtoToUpComingMovie(upComingMovieDto: UpComingMovieDto, listGenreMovieDto: List<GenreMovieDtoItem>): UpComingMovie {
        val dataMovie = upComingMovieDto.results.map {
            movieDtoToMovie(it, listGenreMovieDto)
        }
        return UpComingMovie(upComingMovieDto.page, dataMovie, upComingMovieDto.totalPages, upComingMovieDto.totalResults)
    }

    //convert movieDto to MovieUI (list movie)
    private fun movieDtoToMovie(movieDtoItem: MovieDtoItem, listGenreMovieDto: List<GenreMovieDtoItem>): Movie {

        //genre convert using movieHelper
        val genres = MovieHelper.filterGenre(movieDtoItem.genreIds,listGenreMovieDto )
        val resultGenres = MovieHelper.getGenre(genres)

        return Movie(
            id = movieDtoItem.id,
            title = movieDtoItem.originalTitle,
            posterPath = "https://image.tmdb.org/t/p/original/${movieDtoItem.posterPath}",
            genre = resultGenres,
            releaseDate = movieDtoItem.releaseDate,
            voteCount=movieDtoItem.voteCount,
            voteRange = movieDtoItem.voteAverage
        )
    }

    //convert detail movieDto to detail movie UI
    fun detailMovieDtoToDetailMovie(detailMovieDto: DetailMovieDto, credits: CreditsMovieDto, videoMovieDto: VideosMovieDto, languageDto:List<LanguageMovieDto>, imagesMovieDto: ImagesMovieDto): DetailMovie {
        return DetailMovie(
            id = detailMovieDto.id,
            adult = if (detailMovieDto.adult) "Yes" else "No",
            pgAge = if (detailMovieDto.adult) "+17" else "+13",
            backdropPath = "https://image.tmdb.org/t/p/original/${detailMovieDto.backdropPath}",
            originalTitle = detailMovieDto.originalTitle,
            posterPath = "https://image.tmdb.org/t/p/original/${detailMovieDto.posterPath}",
            releaseDate = detailMovieDto.releaseDate,
            rating = (detailMovieDto.voteAverage/2).toFloat(),
            totalVote = detailMovieDto.voteCount,
            status = detailMovieDto.status,
            overview = detailMovieDto.overview,
            codeLanguage = detailMovieDto.originalLanguage.uppercase(),

            //using movieHelper
            imageMovie = MovieHelper.getImages(imagesMovieDto.backdrops),
            priceMovie = MovieHelper.calculatePriceMovie(detailMovieDto.releaseDate),
            priceFee = MovieHelper.calculatePriceMovie(detailMovieDto.releaseDate)*10/100,
            duration = MovieHelper.calculateDuration(detailMovieDto.runtime),
            genre = MovieHelper.getGenre(detailMovieDto.genres),
            language = MovieHelper.getOriginalLanguage(detailMovieDto.originalLanguage, languageDto),
            videoUrl = MovieHelper.getVideoTrailer(videoMovieDto.resultsVideos),
            director = MovieHelper.getDirector(listCrew = credits.crew),
            actors = MovieHelper.getActors(listActors = credits.cast),
        )
    }


    //mapper request from user to item request
    fun ItemsRequestFromUser.toItemsRequest(): ItemsRequest {
        return ItemsRequest(this.id, this.name, this.price, this.quantity, this.rating, this.imageUrl, this.genreMovie, this.dateWatch)
    }

    //mapper order dto to order response
    fun mapperOrderDtoToOrderResponse(orderDto: OrderDto):OrderResponseUI{
        val dataOrder = orderDto.data
        return OrderResponseUI(dataOrder.orId, dataOrder.orCreatedOn, dataOrder.transaction.redirectUrl)
    }

}