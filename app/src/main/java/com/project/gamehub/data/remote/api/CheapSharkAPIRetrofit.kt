package com.project.gamehub.data.remote.api

import com.project.gamehub.data.remote.dto.DealDetailsDTO
import com.project.gamehub.data.remote.dto.GameDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface CheapSharkAPIRetrofit {
    @GET("deals")
    suspend fun getGames(
        @Query("pageNumber") page: Int = 1,
        @Query("pageSize") limit: Int = 20
    ): List<GameDTO>

    @GET("deals")
    suspend fun getDeal(
        @Query("id", encoded = true) dealId: String
    ): DealDetailsDTO
}