package com.project.gamehub.domain.repository

import com.project.gamehub.domain.model.GameFullInfo
import com.project.gamehub.domain.model.GameShortInfo

interface GameRepository {
    suspend fun getGames(page: Int): Result<List<GameShortInfo>>
    suspend fun getGame(id: String): Result<GameFullInfo>
}