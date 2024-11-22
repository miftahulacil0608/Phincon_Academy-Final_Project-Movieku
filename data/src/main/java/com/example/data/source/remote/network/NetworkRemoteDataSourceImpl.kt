package com.example.data.source.remote.network

import com.example.data.model.dto.network.apiorder.OrderDto
import com.example.data.model.dto.network.apiorder.OrderRequest
import com.example.data.model.dto.network.apiorder.OrderTicketsDto
import com.example.data.model.dto.network.tmdb.CreditsMovieDto
import com.example.data.model.dto.network.tmdb.DetailMovieDto
import com.example.data.model.dto.network.tmdb.GenreMovieDto
import com.example.data.model.dto.network.tmdb.ImagesMovieDto
import com.example.data.model.dto.network.tmdb.LanguageMovieDto
import com.example.data.model.dto.network.tmdb.NowPlayingMovieDto
import com.example.data.model.dto.network.tmdb.SearchMovieDto
import com.example.data.model.dto.network.tmdb.UpComingMovieDto
import com.example.data.model.dto.network.tmdb.VideosMovieDto
import com.example.data.source.remote.network.apiorder.ApiOrderService
import com.example.data.source.remote.network.tmdb.TMDBApiService
import javax.inject.Inject

class NetworkRemoteDataSourceImpl @Inject constructor(
    private val tmdbApiService: TMDBApiService,
    private val orderApiService: ApiOrderService
) : NetworkRemoteDataSourceRepository {

    override suspend fun fetchNowPlayingMovie(releaseDateGte:String, releaseDateLte:String): NowPlayingMovieDto {
        return tmdbApiService.fetchNowPlayingMovie(releaseDateGte, releaseDateLte)
    }

    override suspend fun fetchUpComingMovie(releaseDateGte: String): UpComingMovieDto {
        return tmdbApiService.fetchUpComingMovie(releaseDateGte)
    }

    override suspend fun fetchSearchMovie(movieName: String): SearchMovieDto {
        return tmdbApiService.fetchSearchMovie(query =  movieName)
    }

    override suspend fun fetchGenreMovie(): GenreMovieDto {
        return tmdbApiService.fetchGenre()
    }

    override suspend fun fetchLanguageMovie(): List<LanguageMovieDto> {
        return tmdbApiService.fetchLanguage()
    }

    override suspend fun fetchDetailMovie(movieId: Int): DetailMovieDto {
        return tmdbApiService.fetchDetailMovie(movieId)
    }

    override suspend fun fetchMovieVideos(movieId: Int): VideosMovieDto {
        return tmdbApiService.fetchMovieVideos(movieId)
    }

    override suspend fun fetchCreditsMovie(movieId: Int): CreditsMovieDto {
        return tmdbApiService.fetchCreditsMovie(movieId)
    }

    override suspend fun fetchImagesMovie(movieId: Int): ImagesMovieDto {
        return tmdbApiService.fetchImagesMovie(movieId)
    }

    override suspend fun postOrderMovie(orderRequest: OrderRequest): OrderDto {
        return orderApiService.orderMovie(orderRequest)
    }

    override suspend fun fetchOrdersTicketsPaid(searchByEmail: String): OrderTicketsDto {
        return orderApiService.fetchOrderTickets(searchByEmail = searchByEmail)
    }
}