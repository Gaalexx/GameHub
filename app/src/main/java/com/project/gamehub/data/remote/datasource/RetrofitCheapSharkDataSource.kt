package com.project.gamehub.data.remote.datasource

import com.project.gamehub.data.remote.GamesAPI
import com.project.gamehub.data.remote.api.CheapSharkAPIRetrofit
import com.project.gamehub.data.remote.dto.GameDTO
import jakarta.inject.Inject

class RetrofitCheapSharkDataSource @Inject constructor(
    private val api: CheapSharkAPIRetrofit
) : GamesAPI {
    override suspend fun getGames(
        limit: Int,
        page: Int
    ): List<GameDTO> {
        return api.getGames(
            limit = limit,
            page = page
        )
    }
}