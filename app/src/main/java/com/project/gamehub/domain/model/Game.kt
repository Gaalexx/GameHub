package com.project.gamehub.domain.model

import androidx.compose.runtime.Immutable
import kotlin.random.Random

@Immutable
data class Game(
    val gameId: String,
    val name: String = "name $gameId",
    val description: String = name,
    val photoUrl: String? = null
)