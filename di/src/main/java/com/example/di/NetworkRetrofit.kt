package com.example.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import kotlin.math.log

class TMDBInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val bearerTokenTheMovieDb =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkN2I5ZTc0ZjE0ZjJkMjIwYmIzMjFkNjk5MTBhMzc1YSIsIm5iZiI6MTczMDYyOTA1NS4yOTUzNzA2LCJzdWIiOiI2NzI0NDFjZGMxYzc0MzNlYmJjNDE2NGYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.52KNw2IWGEEr2dDVZXF_SKMMErAk01t3Fww6Th4DDhc"
        val request = chain.request().newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $bearerTokenTheMovieDb")
            .build()
        return chain.proceed(request)
    }
}

// Interceptor untuk Order
class OrderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val secretKey = ">9tXB)|qQ@;{4l799f9k;Xa!a%BkXW"
        val request = chain.request().newBuilder()
            .addHeader("x-secret-app", secretKey)
            .addHeader("x-user-id", "1")
            .build()
        return chain.proceed(request)
    }
}