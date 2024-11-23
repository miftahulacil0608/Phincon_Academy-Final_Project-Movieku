package com.example.di

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class TMDBInterceptor : Interceptor {
    private val apiKey = BuildConfig.THE_MOVIE_DB_API_KEY
    override fun intercept(chain: Interceptor.Chain): Response {
        val bearerTokenTheMovieDb = apiKey
        val request = chain.request().newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $bearerTokenTheMovieDb")
            .build()
        return chain.proceed(request)
    }
}

// Interceptor untuk Order
class OrderInterceptor : Interceptor {
    private val apiKey = BuildConfig.SECRET_KEY_API_ORDER
    override fun intercept(chain: Interceptor.Chain): Response {
        val secretKey = apiKey
        val request = chain.request().newBuilder()
            .addHeader("x-secret-app", secretKey)
            .addHeader("x-user-id", "1")
            .build()
        return chain.proceed(request)
    }
}