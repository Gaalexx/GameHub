package com.project.gamehub.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random



@Serializable
data class GameDTO(
    val internalName: String,

    val title: String,

    val metacriticLink: String?,

    @SerialName("dealID")
    val dealId: String,

    @SerialName("storeID")
    val storeId: String,

    @SerialName("gameID")
    val gameId: String,

    val salePrice: String,

    val normalPrice: String,

    val isOnSale: String,

    val savings: String,

    val metacriticScore: String,

    val steamRatingText: String?,

    val steamRatingPercent: String,

    val steamRatingCount: String,

    @SerialName("steamAppID")
    val steamAppId: String?,

    val releaseDate: Long,

    val lastChange: Long,

    val dealRating: String,

    @SerialName("thumb")
    val photoUrl: String?
)
@Serializable
data class GamesResponseDto(
    val data: List<GameDTO>
)