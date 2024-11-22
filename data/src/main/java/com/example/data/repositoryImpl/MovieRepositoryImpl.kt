package com.example.data.repositoryImpl

import android.util.Log
import com.example.data.model.dto.network.apiorder.OrderRequest
import com.example.data.source.local.LocalDataSourceRepository
import com.example.data.source.remote.network.NetworkRemoteDataSourceRepository
import com.example.data.utils.MapperToDomainData
import com.example.data.utils.MapperToDomainData.toWatchListEntity
import com.example.data.utils.OrderHelper
import com.example.data.utils.OrderHelper.toItemsRequest
import com.example.domain.model.movie.DetailMovie
import com.example.domain.model.movie.NowPlayingMovie
import com.example.domain.model.movie.SearchMovie
import com.example.domain.model.movie.UpComingMovie
import com.example.domain.model.movie.order.OrderRequestFromUser
import com.example.domain.model.movie.order.OrderResponseUI
import com.example.domain.model.movie.tickets.Ticket
import com.example.domain.model.movie.watchlist.WatchListUI
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val networkRemoteDataSourceRepository: NetworkRemoteDataSourceRepository,
    private val localDataSourceRepository: LocalDataSourceRepository
) :
    MovieRepository {

    override suspend fun getNowPlayingMovie(
        releaseDateGte: String,
        releaseDateLte: String
    ): NowPlayingMovie {
        val listGenre = networkRemoteDataSourceRepository.fetchGenreMovie().genres
        return MapperToDomainData.nowPlayingMovieDtoToNowPlayingMovie(
            networkRemoteDataSourceRepository.fetchNowPlayingMovie(releaseDateGte, releaseDateLte),
            listGenre
        )
    }

    override suspend fun getUpComingMovie(releaseDateGte: String): UpComingMovie {
        val listGenre = networkRemoteDataSourceRepository.fetchGenreMovie().genres
        return MapperToDomainData.upComingMovieDtoToUpComingMovie(
            networkRemoteDataSourceRepository.fetchUpComingMovie(
                releaseDateGte
            ), listGenre
        )
    }

    override suspend fun searchMovie(movieName: String): SearchMovie {
        return MapperToDomainData.searchMovieDtoToSearchMovie(networkRemoteDataSourceRepository.fetchSearchMovie(movieName))
    }


    override suspend fun getDetailMovie(movieId: Int): DetailMovie {
        val credits = networkRemoteDataSourceRepository.fetchCreditsMovie(movieId)
        val videos = networkRemoteDataSourceRepository.fetchMovieVideos(movieId)
        val language = networkRemoteDataSourceRepository.fetchLanguageMovie()
        val detailMovieDto = networkRemoteDataSourceRepository.fetchDetailMovie(movieId)
        val images = networkRemoteDataSourceRepository.fetchImagesMovie(movieId)
        return MapperToDomainData.detailMovieDtoToDetailMovie(
            detailMovieDto,
            credits,
            videos,
            language,
            images
        )
    }


    override suspend fun insertWatchList(watchListUI: WatchListUI) {
        val dataStoreUser = localDataSourceRepository.getSettings()
        val email = dataStoreUser.email
        Log.d("email", "insertWatchList: $email")
        if (email.isNotEmpty()) {
            localDataSourceRepository.insertWatchList(watchListUI.toWatchListEntity(email))
        }
    }

    override suspend fun getWatchList(): Flow<List<WatchListUI>> {
        val dataStoreUser = localDataSourceRepository.getSettings()
        val email = dataStoreUser.email
        return localDataSourceRepository.getWatchList(email).map {
            it.map { watchListItem ->
                watchListItem.toWatchListEntity()
            }
        }
    }

    override suspend fun deleteWatchList(movieId: Int) {
        val dataStoreUser = localDataSourceRepository.getSettings()
        val email = dataStoreUser.email
        Log.d("emailDelete", "insertWatchList: $email")
        if (email.isNotEmpty()) {
            localDataSourceRepository.deleteWatchList(email, movieId)
        }
    }

    override suspend fun isWatchListExist(movieId: Int): Boolean {
        val dataStore = localDataSourceRepository.getSettings()
        val email = dataStore.email
        return localDataSourceRepository.isWatchList(email, movieId)
    }

    override suspend fun orderMovie(orderRequestFromUser: OrderRequestFromUser): OrderResponseUI {
        val email = localDataSourceRepository.getSettings().email
        val convertItemRequestUserToItemRequest =
            orderRequestFromUser.itemsRequest.map { it.toItemsRequest() }
        val orderRequest = OrderRequest(
            orderRequestFromUser.amount,
            email,
            convertItemRequestUserToItemRequest
        )
        return OrderHelper.mapperOrderDtoToOrderResponse(
            networkRemoteDataSourceRepository.postOrderMovie(
                orderRequest
            )
        )
    }

    override suspend fun getTickets(): List<Ticket> {
        val email = localDataSourceRepository.getSettings().email
        val fetchOrderTickets = networkRemoteDataSourceRepository.fetchOrdersTicketsPaid(email)
        val resultTickets = OrderHelper.mapperAllOrdersDtoToTickets(fetchOrderTickets)
        return resultTickets
    }


}