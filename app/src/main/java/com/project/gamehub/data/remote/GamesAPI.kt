package com.project.gamehub.data.remote

import com.project.gamehub.data.remote.dto.DealDetailsDTO
import com.project.gamehub.data.remote.dto.GameDTO

interface GamesAPI {
    suspend fun getGames(limit: Int = 20, page: Int = 0): List<GameDTO>
    suspend fun getGame(id: String): DealDetailsDTO
}