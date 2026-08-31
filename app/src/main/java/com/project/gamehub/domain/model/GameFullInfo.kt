package com.project.gamehub.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class GameFullInfo(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String?,
    val rating: String,
    val price: String,
    val saved: Boolean = false
)