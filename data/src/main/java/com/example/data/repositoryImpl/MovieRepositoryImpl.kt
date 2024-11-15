package com.example.data.repositoryImpl

import com.example.data.model.dto.network.apiorder.ItemsRequest
import com.example.data.model.dto.network.apiorder.OrderRequest
import com.example.data.source.remote.network.NetworkRemoteDataSourceRepository
import com.example.data.utils.MapperToDomainData
import com.example.domain.model.DetailMovie
import com.example.domain.model.ItemsRequestFromUser
import com.example.domain.model.NowPlayingMovie
import com.example.domain.model.OrderRequestFromUser
import com.example.domain.model.OrderResponseUI
import com.example.domain.model.UpComingMovie
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val networkRemoteDataSourceRepository: NetworkRemoteDataSourceRepository) :
    MovieRepository {

    override suspend fun getNowPlayingMovie(): NowPlayingMovie {
        val listGenre = networkRemoteDataSourceRepository.fetchGenreMovie().genres
        return MapperToDomainData.nowPlayingMovieDtoToNowPlayingMovie(networkRemoteDataSourceRepository.fetchNowPlayingMovie(), listGenre)
    }

    override suspend fun getUpComingMovie(): UpComingMovie {
        val listGenre = networkRemoteDataSourceRepository.fetchGenreMovie().genres
        return MapperToDomainData.upComingMovieDtoToUpComingMovie(networkRemoteDataSourceRepository.fetchUpComingMovie(), listGenre)
    }

    override suspend fun getDetailMovie(movieId:Int): DetailMovie {
        val credits = networkRemoteDataSourceRepository.fetchCreditsMovie(movieId)
        val videos = networkRemoteDataSourceRepository.fetchMovieVideos(movieId)
        val language = networkRemoteDataSourceRepository.fetchLanguageMovie()
        val detailMovieDto = networkRemoteDataSourceRepository.fetchDetailMovie(movieId)
        val images = networkRemoteDataSourceRepository.fetchImagesMovie(movieId)
        return MapperToDomainData.detailMovieDtoToDetailMovie(detailMovieDto, credits, videos, language, images)
    }

    override suspend fun orderMovie(orderRequestFromUser: OrderRequestFromUser): OrderResponseUI {
        val convertItemRequestUserToItemRequest = orderRequestFromUser.itemsRequest.map { it.toItemsRequest() }
        val orderRequesting = OrderRequest(orderRequestFromUser.amount, orderRequestFromUser.email, convertItemRequestUserToItemRequest)
        return MapperToDomainData.mapperOrderDtoToOrderResponse(networkRemoteDataSourceRepository.postOrderMovie(orderRequesting))
    }

    companion object{
        fun ItemsRequestFromUser.toItemsRequest():ItemsRequest{
           return ItemsRequest(this.id, this.name, this.price, this.quantity)

        }
    }

}