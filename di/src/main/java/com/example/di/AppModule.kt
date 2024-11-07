package com.example.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.repositoryImpl.MovieRepositoryImpl
import com.example.data.source.local.datastore.UserDataStore
import com.example.data.source.remote.RemoteDataSourceImpl
import com.example.data.source.remote.RemoteDataSourceRepository
import com.example.data.source.remote.network.TMDBApiService
import com.example.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val Context.datastore by preferencesDataStore(name = "user_datastore_preferences")

    @Provides
    @Singleton
    fun providesDataStorePreferences(@ApplicationContext context:Context):DataStore<Preferences>{
        return context.datastore
    }

    @Provides
    @Singleton
    fun providesUserDataStore(@ApplicationContext context: Context):UserDataStore{
        return UserDataStore(context.applicationContext.datastore)
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesInterceptor( userDataStore: UserDataStore):Interceptor{
        return Interceptor{
            val chain = it.request()
            val bearerTokenTheMovieDb = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkN2I5ZTc0ZjE0ZjJkMjIwYmIzMjFkNjk5MTBhMzc1YSIsIm5iZiI6MTczMDYyOTA1NS4yOTUzNzA2LCJzdWIiOiI2NzI0NDFjZGMxYzc0MzNlYmJjNDE2NGYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.52KNw2IWGEEr2dDVZXF_SKMMErAk01t3Fww6Th4DDhc"
            val requestHeaders = chain.newBuilder()
                .addHeader("accept","application/json")
                .addHeader("Authorization","Bearer $bearerTokenTheMovieDb")
                .build()
            it.proceed(requestHeaders)
        }
    }

    @Provides
    @Singleton
    fun providesClientOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor
    ):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .connectTimeout(0, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesNetwork(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit):TMDBApiService{
        return retrofit.create(TMDBApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSourceRepository(tmdbApiService:TMDBApiService):RemoteDataSourceRepository{
        return RemoteDataSourceImpl(tmdbApiService)
    }

    @Provides
    @Singleton
    fun providesMovieRepository(remoteDataSourceRepository: RemoteDataSourceRepository):MovieRepository{
        return MovieRepositoryImpl(remoteDataSourceRepository)
    }


}