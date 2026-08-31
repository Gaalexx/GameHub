package com.project.gamehub.data.remote

import com.project.gamehub.data.remote.dto.SteamGameResponseDTO

interface GameInfoAPI {
    suspend fun getGameInfo(gameId: String): SteamGameResponseDTO?
}