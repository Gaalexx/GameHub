package com.project.gamehub.data.remote.datasource

import com.project.gamehub.data.remote.GameInfoAPI
import com.project.gamehub.data.remote.api.CheapSharkAPIRetrofit
import com.project.gamehub.data.remote.api.SteamAPIRetrofit
import com.project.gamehub.data.remote.dto.SteamGameResponseDTO
import javax.inject.Inject

class RetrofitSteamDataSource @Inject constructor(
    private val api: SteamAPIRetrofit
): GameInfoAPI {
    override suspend fun getGameInfo(gameId: String): SteamGameResponseDTO? {
        return api.getGameInfo(gameId)[gameId]
    }

}