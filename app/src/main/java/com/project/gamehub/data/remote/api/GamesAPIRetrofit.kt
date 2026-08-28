package com.project.gamehub.data.remote.api

import com.project.gamehub.BuildConfig
import com.project.gamehub.data.remote.dto.GameDTO
import com.project.gamehub.data.remote.dto.GamesResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GamesAPIRetrofit {
    @GET("deals")
    suspend fun getGames(
        @Query("pageNumber") page: Int = 1,
        @Query("pageSize") limit: Int = 20
    ): List<GameDTO>

    @GET("games")
    suspend fun getGameById(
        @Query("") smth: String
    )
}