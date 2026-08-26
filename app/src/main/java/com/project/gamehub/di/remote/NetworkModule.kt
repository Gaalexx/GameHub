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
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlin.jvm.java
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.kotlinx.serialization.asConverterFactory


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
    fun provideRetrofit(
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.gamebrain_api_url)
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
abstract class NetworkAbstractModule{
    @Binds
    abstract fun bindGamesAPIImpl(
        impl: RetrofitDataSource
    ): GamesAPI

    @Binds
    abstract fun bindGamesRepo(
        impl: GameRepositoryImpl
    ): GameRepository
}