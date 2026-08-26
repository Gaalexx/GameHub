package com.project.gamehub.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random



@Serializable
data class GameDTO(
    @SerialName("id")
    val gameId: String,
    val title: String,
    @SerialName("short_description")
    val description: String? = null,
    @SerialName("cover_image_url")
    val photoUrl: String?
)
@Serializable
data class GamesResponseDto(
    val data: List<GameDTO>
)