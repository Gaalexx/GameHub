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

@Serializable
data class DealDetailsDTO(
    val gameInfo: DealGameInfoDTO,
    val cheaperStores: List<CheaperStoreDTO>,
    val cheapestPrice: CheapestPriceDTO
)

@Serializable
data class DealGameInfoDTO(
    @SerialName("storeID")
    val storeId: String,

    @SerialName("gameID")
    val gameId: String,

    val name: String,

    @SerialName("steamAppID")
    val steamAppId: String?,

    val salePrice: String,

    val retailPrice: String,

    val steamRatingText: String?,

    val steamRatingPercent: String,

    val steamRatingCount: String,

    val metacriticScore: String,

    val metacriticLink: String?,

    val releaseDate: Long,

    val publisher: String?,

    @SerialName("thumb")
    val photoUrl: String?
)

@Serializable
data class CheaperStoreDTO(
    @SerialName("dealID")
    val dealId: String,

    @SerialName("storeID")
    val storeId: String,

    val salePrice: String,

    val retailPrice: String
)

@Serializable
data class CheapestPriceDTO(
    val price: String? = null,
    val date: Long? = null
)