package com.project.gamehub.data.remote

import com.project.gamehub.data.remote.dto.GameDTO

interface GamesAPI {
    suspend fun getGames(query: String = "", limit: Int = 20, page: Int = 0): List<GameDTO>
}