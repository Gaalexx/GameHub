package com.project.gamehub.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "saved_games")
data class GameEntity(
    @PrimaryKey
    val gameId: Int,
    val steamId: String,
    val dealId: String,
    val name: String,
    val imageUrl: String?,
    val description: String,
    val price: String,
    val rating: String
)
