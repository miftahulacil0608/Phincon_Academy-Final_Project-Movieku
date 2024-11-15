package com.example.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
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
import com.example.data.source.remote.network.apiorder.ApiOrderService
import com.example.data.source.remote.network.tmdb.TMDBApiService
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
    fun providesTMDBInterceptor():TMDBInterceptor{
        return TMDBInterceptor()
    }

    @Provides
    @Singleton
    fun providesOrderInterceptor(): OrderInterceptor {
        return OrderInterceptor()
    }

    @Provides
    @Singleton
    fun providesTMDBClientOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        tmdbInterceptor: TMDBInterceptor,
        orderInterceptor: OrderInterceptor
    ):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tmdbInterceptor)
            .addInterceptor(orderInterceptor)
            .connectTimeout(0, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun providesTMDBAPI(tmdbOkHttpClient: OkHttpClient): TMDBApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(tmdbOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(TMDBApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesOrderAPI(orderOkhttpClient:OkHttpClient):ApiOrderService{
        return Retrofit.Builder()
            .baseUrl("https://phincon-academy-api.onrender.com/")
            .client(orderOkhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiOrderService::class.java)
    }


    @Provides
    @Singleton
    fun provideRemoteDataSourceRepository(tmdbApiService: TMDBApiService, orderApiOrderService: ApiOrderService): NetworkRemoteDataSourceRepository {
        return NetworkRemoteDataSourceImpl(tmdbApiService, orderApiOrderService)
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