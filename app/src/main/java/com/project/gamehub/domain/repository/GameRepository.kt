package com.project.gamehub.domain.repository

import com.project.gamehub.domain.model.Game

interface GameRepository {
    suspend fun getGames(page: Int): Result<List<Game>>
}