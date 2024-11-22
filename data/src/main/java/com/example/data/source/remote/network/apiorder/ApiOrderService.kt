package com.example.data.source.remote.network.apiorder

import com.example.data.model.dto.network.apiorder.OrderDto
import com.example.data.model.dto.network.apiorder.OrderRequest
import com.example.data.model.dto.network.apiorder.OrderTicketsDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiOrderService {
    @POST("phincon/api/order/snap")
    suspend fun orderMovie(@Body orderRequest: OrderRequest): OrderDto

    @GET("phincon/api/orders")
    suspend fun fetchOrderTickets(
        @Query("orderPaymentStatus") orderPaymentStatus: String = "settlement",
        @Query("search") searchByEmail: String
    ): OrderTicketsDto

}