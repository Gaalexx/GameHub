package com.project.gamehub.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import com.project.gamehub.data.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedGamesDao {
    @Query("SELECT * FROM saved_games")
    fun getGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM saved_games WHERE steamId = :id")
    suspend fun getGameBySteamId(id: String): GameEntity?

    @Query("SELECT * FROM saved_games WHERE dealId = :id")
    suspend fun getGameByDealId(id: String): GameEntity?

    @Insert
    suspend fun insert(game: GameEntity)

    @Delete
    suspend fun delete(game: GameEntity)

    @Query("DELETE FROM saved_games WHERE steamId = :id")
    suspend fun deleteBySteamId(id: String)
}