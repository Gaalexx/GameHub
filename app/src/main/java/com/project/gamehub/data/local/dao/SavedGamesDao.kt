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

    @Insert
    suspend fun insert(game: GameEntity)

    @Delete
    suspend fun delete(game: GameEntity)
}