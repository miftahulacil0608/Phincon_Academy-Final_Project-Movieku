package com.example.data.model.dto.network.apiorder


import com.google.gson.annotations.SerializedName

data class OrderTicketsDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("totalOrders")
    val totalOrders: Int,
    @SerializedName("totalPages")
    val totalPages: Int
) {
    data class Data(
        @SerializedName("details")
        val details: List<Detail>,
        @SerializedName("or_created_on")
        val orCreatedOn: String,
        @SerializedName("or_email")
        val orEmail: String,
        @SerializedName("or_id")
        val orId: Int,
        @SerializedName("or_payment_status")
        val orPaymentStatus: String,
        @SerializedName("or_platform_id")
        val orPlatformId: String,
        @SerializedName("or_status")
        val orStatus: String,
        @SerializedName("or_token_id")
        val orTokenId: String,
        @SerializedName("or_total_price")
        val orTotalPrice: Int,
        @SerializedName("promos")
        val promos: List<Any>
    ) {
        data class Detail(
            @SerializedName("od_products")
            val odProducts: List<OdProduct>
        ) {
            data class OdProduct(
                @SerializedName("cinema")
                val cinema: String,
                @SerializedName("code_language")
                val codeLanguage: String,
                @SerializedName("code_ticket")
                val codeTicket: String,
                @SerializedName("date_watch")
                val dateWatch: String,
                @SerializedName("duration")
                val duration: String,
                @SerializedName("genre")
                val genre: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("image_url")
                val imageUrl: String,
                @SerializedName("name")
                val name: String,
                @SerializedName("price")
                val price: Int,
                @SerializedName("quantity")
                val quantity: Int,
                @SerializedName("rate_age")
                val rateAge: String,
                @SerializedName("seat_number")
                val seatNumber: List<Int>,
                @SerializedName("seat_row")
                val seatRow: String,
                @SerializedName("studio")
                val studio: String,
                @SerializedName("time_watch")
                val timeWatch: String
            )
        }
    }
}