package com.project.gamehub.domain.repository

import com.project.gamehub.domain.model.GameFullInfo
import com.project.gamehub.domain.model.GameShortInfo
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun getGames(page: Int): Result<List<GameShortInfo>>
    suspend fun getGame(id: String): Result<GameFullInfo>
    suspend fun getSavedGamesFull(): Flow<List<GameFullInfo>>
    suspend fun observeSavedGamesShort(): Flow<List<GameShortInfo>>
    suspend fun getSavedGameByDealId(dealId: String): GameFullInfo?
    suspend fun getSavedGameBySteamId(steamId: String): GameFullInfo?
    suspend fun saveGame(game: GameFullInfo, dealId: String)

    suspend fun deleteBySteamId(id: String)
}