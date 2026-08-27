package com.project.gamehub.di.remote

import com.project.gamehub.BuildConfig
import com.project.gamehub.data.remote.GamesAPI
import com.project.gamehub.data.remote.api.GamesAPIRetrofit
import com.project.gamehub.data.remote.datasource.RetrofitDataSource
import com.project.gamehub.data.repository.GameRepositoryImpl
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
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
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
    fun provideRetrofit(
        client: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.gamebrain_api_url)
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
    fun provideGamesApi(
        retrofit: Retrofit
    ): GamesAPIRetrofit {
        return retrofit.create(GamesAPIRetrofit::class.java)
    }


}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkAbstractModule {
    @Binds
    abstract fun bindGamesAPIImpl(
        impl: RetrofitDataSource
    ): GamesAPI

    @Binds
    abstract fun bindGamesRepo(
        impl: GameRepositoryImpl
    ): GameRepository
}