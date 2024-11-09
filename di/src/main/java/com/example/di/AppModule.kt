package com.example.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.model.SettingData
import com.example.data.repositoryImpl.AuthRepositoryImpl
import com.example.data.repositoryImpl.MovieRepositoryImpl
import com.example.data.repositoryImpl.UserSettingRepositoryImpl
import com.example.data.source.local.LocalDataSourceRepository
import com.example.data.source.local.LocalDataSourceRepositoryImpl
import com.example.data.source.local.datastore.SettingsDataStore
import com.example.data.source.remote.network.NetworkRemoteDataSourceImpl
import com.example.data.source.remote.network.NetworkRemoteDataSourceRepository
import com.example.data.source.remote.firebase.FireBaseRemoteDataSourceImpl
import com.example.data.source.remote.firebase.FireBaseRemoteDataSourceRepository
import com.example.data.source.remote.network.TMDBApiService
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.UserSettingRepository
import com.google.firebase.auth.FirebaseAuth
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
    //Firebase
    @Provides
    @Singleton
    fun providesFireBaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFireBaseAuthenticationRepository(firebaseAuth: FirebaseAuth):FireBaseRemoteDataSourceRepository{
        return FireBaseRemoteDataSourceImpl(firebaseAuth)
    }

    //End Firebase

    //datastore
    private val Context.datastore by preferencesDataStore(name = "user_datastore_preferences")

    @Provides
    @Singleton
    fun providesDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.datastore
    }

    @Provides
    @Singleton
    fun providesUserDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context.applicationContext.datastore)
    }

    @Provides
    @Singleton
    fun provideLocalDataSourceRepository(datastore:SettingsDataStore):LocalDataSourceRepository{
        return LocalDataSourceRepositoryImpl(datastore)
    }

    //end datastore

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesInterceptor(settingsDataStore: SettingsDataStore): Interceptor {
        return Interceptor {
            val chain = it.request()
            val bearerTokenTheMovieDb =
                "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkN2I5ZTc0ZjE0ZjJkMjIwYmIzMjFkNjk5MTBhMzc1YSIsIm5iZiI6MTczMDYyOTA1NS4yOTUzNzA2LCJzdWIiOiI2NzI0NDFjZGMxYzc0MzNlYmJjNDE2NGYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.52KNw2IWGEEr2dDVZXF_SKMMErAk01t3Fww6Th4DDhc"
            val requestHeaders = chain.newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer $bearerTokenTheMovieDb")
                .build()
            it.proceed(requestHeaders)
        }
    }

    @Provides
    @Singleton
    fun providesClientOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor
    ): OkHttpClient {
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
    fun providesNetwork(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): TMDBApiService {
        return retrofit.create(TMDBApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSourceRepository(tmdbApiService: TMDBApiService): NetworkRemoteDataSourceRepository {
        return NetworkRemoteDataSourceImpl(tmdbApiService)
    }

    @Provides
    @Singleton
    fun providesMovieRepository(networkRemoteDataSourceRepository: NetworkRemoteDataSourceRepository): MovieRepository {
        return MovieRepositoryImpl(networkRemoteDataSourceRepository)
    }


    @Provides
    @Singleton
    fun providesAuthenticationRepository(fireBaseRemoteDataSourceRepository: FireBaseRemoteDataSourceRepository):AuthRepository{
        return AuthRepositoryImpl(fireBaseRemoteDataSourceRepository)
    }

    @Provides
    @Singleton
    fun providesUserSettingRepository(localDataSourceRepository: LocalDataSourceRepository):UserSettingRepository{
        return UserSettingRepositoryImpl(localDataSourceRepository)
    }



}