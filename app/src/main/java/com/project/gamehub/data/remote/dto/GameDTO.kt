package com.project.gamehub.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random



@Serializable
data class GameDTO(
    @SerialName("gameID")
    val gameId: String,
    val title: String,
    @SerialName("thumb")
    val photoUrl: String?
)
@Serializable
data class GamesResponseDto(
    val data: List<GameDTO>
)