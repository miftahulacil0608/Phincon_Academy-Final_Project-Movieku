package com.example.data.source.remote.network.apiorder

import com.example.data.model.dto.network.apiorder.OrderDto
import com.example.data.model.dto.network.apiorder.OrderRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiOrderService {
    @POST("phincon/api/order/snap")
    suspend fun orderMovie(@Body orderRequest: OrderRequest):OrderDto


}