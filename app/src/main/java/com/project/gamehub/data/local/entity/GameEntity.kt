package com.project.gamehub.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.project.gamehub.domain.model.GameFullInfo

@Entity(tableName = "saved_games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val gameId: Int = 0,
    val steamId: String,
    val dealId: String,
    val name: String,
    val imageUrl: String?,
    val description: String,
    val price: String,
    val rating: String
) {
    fun toDomain(): GameFullInfo =
        GameFullInfo(
            id = this.steamId,
            name = this.name,
            description = this.description,
            photoUrl = this.imageUrl,
            rating = this.rating,
            price = this.price,
        )
}
