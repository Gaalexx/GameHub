package com.project.gamehub.domain.model

import androidx.compose.runtime.Immutable
import kotlin.random.Random

@Immutable
data class Game(
    val gameId: Int = 0,
    val name: String = "name $gameId",
    val description: String = name,
    val rating: Float = Random.nextFloat().coerceIn(0f, 10f),
    val photoUrl: String? = null
)