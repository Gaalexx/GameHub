package com.project.gamehub.data.remote.api

import com.project.gamehub.BuildConfig
import com.project.gamehub.data.remote.dto.GamesResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GamesAPIRetrofit {
    @GET("games")
    suspend fun getGames(
//        @Header("Authorization") apiKey: String = BuildConfig.gamebrain_api_key,
//        @Query("q") query: String = "",
        @Query("page") page: Int = 1,
        @Query("page_size") limit: Int = 20
    ): GamesResponseDto
}