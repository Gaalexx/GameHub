package com.project.gamehub.data.local.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.project.gamehub.data.local.dao.SavedGamesDao
import com.project.gamehub.data.local.entity.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 1
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): SavedGamesDao
}