package com.project.gamehub.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class GameShortInfo(
    val gameId: String,
    val name: String = "name $gameId",
//    val description: String = name,
    val photoUrl: String? = null,
    val dealId: String? = null
)