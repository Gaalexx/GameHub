package com.project.gamehub.data.remote.api

import com.project.gamehub.data.remote.dto.SteamGameResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamAPIRetrofit {

    @GET("api/appdetails")
    suspend fun getGameInfo(
        @Query("appids") steamAppId: String
    ): Map<String, SteamGameResponseDTO>

}