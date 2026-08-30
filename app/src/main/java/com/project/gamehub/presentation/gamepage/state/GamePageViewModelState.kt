package com.project.gamehub.presentation.gamepage.state

import androidx.compose.runtime.Immutable


@Immutable
data class GamePageViewModelState(
    val name: String = "",
    val photoUrl: String? = null,
    val rating: String = "",
    val price: String = "",
    val description: String = "",
    val isInLibrary: Boolean = false,
    val isLoading: Boolean = true
)