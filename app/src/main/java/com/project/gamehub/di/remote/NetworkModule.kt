package com.project.gamehub.di.remote

import com.project.gamehub.BuildConfig
import com.project.gamehub.data.remote.GameInfoAPI
import com.project.gamehub.data.remote.GamesAPI
import com.project.gamehub.data.remote.api.CheapSharkAPIRetrofit
import com.project.gamehub.data.remote.api.SteamAPIRetrofit
import com.project.gamehub.data.remote.datasource.RetrofitCheapSharkDataSource
import com.project.gamehub.data.remote.datasource.RetrofitSteamDataSource
import com.project.gamehub.data.repository.GameRepositoryImpl
import com.project.gamehub.di.annotations.CheapSharkHttpClient
import com.project.gamehub.di.annotations.CheapSharkRetrofit
import com.project.gamehub.di.annotations.SteamRetrofit
import com.project.gamehub.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    @CheapSharkHttpClient
    fun provideCheapSharkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .header("User-Agent", "GameHub/1.0")
                .build()

            chain.proceed(request)
        }
        .build()

    @Provides
    @Singleton
    @CheapSharkRetrofit
    fun provideCheapSharkRetrofit(
        @CheapSharkHttpClient client: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.cheapSharkApiUrl)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    @SteamRetrofit
    fun provideSteamRetrofit(
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.steamApiUrl)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideGamesApi(
        @CheapSharkRetrofit retrofit: Retrofit
    ): CheapSharkAPIRetrofit {
        return retrofit.create(CheapSharkAPIRetrofit::class.java)
    }

    @Provides
    @Singleton
    fun provideSteamApi(
        @SteamRetrofit retrofit: Retrofit
    ): SteamAPIRetrofit {
        return retrofit.create(SteamAPIRetrofit::class.java)
    }


}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkAbstractModule {
    @Binds
    abstract fun bindGamesAPIImpl(
        impl: RetrofitCheapSharkDataSource
    ): GamesAPI

    @Binds
    abstract fun bindGameAPIImpl(
        impl: RetrofitSteamDataSource
    ): GameInfoAPI

    @Binds
    abstract fun bindGamesRepo(
        impl: GameRepositoryImpl
    ): GameRepository

}