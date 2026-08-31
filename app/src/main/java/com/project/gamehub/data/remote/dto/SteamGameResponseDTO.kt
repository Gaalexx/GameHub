package com.project.gamehub.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SteamGameResponseDTO(
    val success: Boolean,
    val data: SteamGameDTO? = null
)

@Serializable
data class SteamGameDTO(
    val type: String,

    val name: String,

    @SerialName("steam_appid")
    val steamAppId: Int,

    @SerialName("short_description")
    val description: String?,

    @SerialName("header_image")
    val photoUrl: String?,

    val developers: List<String> = emptyList(),

    val publishers: List<String> = emptyList(),

    val genres: List<GenreDTO> = emptyList()
)

@Serializable
data class GenreDTO(
    val id: String,
    val description: String
)