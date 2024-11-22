package com.example.data.utils

import android.util.Log
import com.example.data.model.dto.network.apiorder.ItemsRequest
import com.example.data.model.dto.network.apiorder.OrderDto
import com.example.data.model.dto.network.apiorder.OrderTicketsDto
import com.example.domain.model.movie.order.ItemsRequestFromUser
import com.example.domain.model.movie.order.OrderResponseUI
import com.example.domain.model.movie.tickets.Ticket

object OrderHelper {

    //mapper request from user to item request
    fun ItemsRequestFromUser.toItemsRequest(): ItemsRequest {
        return ItemsRequest(
            this.dateWatch,
            this.timeWatch,
            this.id,
            this.price,
            this.quantity,
            this.name,
            this.imageUrl,
            this.genre,
            this.duration,
            this.pgAge,
            this.codeLanguage,
            this.cinema,
            this.studio,
            this.seatRow,
            this.seatNumber,
            this.codeTicket
        )
    }

    //mapper order dto to order response
    fun mapperOrderDtoToOrderResponse(orderDto: OrderDto): OrderResponseUI {
        val dataOrder = orderDto.data
        return OrderResponseUI(
            dataOrder.orId,
            dataOrder.orCreatedOn,
            dataOrder.transaction.redirectUrl
        )
    }

    //mapper data all orders dto to tickets
    fun mapperAllOrdersDtoToTickets(allOrderDto:OrderTicketsDto):List<Ticket>{
        val tickets = mutableListOf<Ticket>()
        allOrderDto.data.forEach {order->
            order.details.forEach {detail->
                detail.odProducts.forEach {product->
                        tickets.add(
                        Ticket(
                            cinema = product.cinema,
                            codeLanguage = product.codeLanguage,
                            codeTicket = product.codeTicket,
                            dateWatch = product.dateWatch,
                            duration = product.duration,
                            genre = product.genre,
                            id = product.id,
                            imageUrl = product.imageUrl,
                            name = product.name,
                            rateAge = product.rateAge,
                            price = order.orTotalPrice,
                            quantity = product.quantity,
                            seatNumbers = product.seatNumber.joinToString(","),
                            seatRow = product.seatRow,
                            studio = product.studio,
                            timeWatch = product.timeWatch
                        )
                    )
                }
            }
        }
        return tickets
    }
}