package com.project.gamehub.presentation.gamepage.state

import androidx.compose.runtime.Immutable
import com.project.gamehub.presentation.gamepage.viewmodel.GamePageViewModelError


@Immutable
data class GamePageViewModelState(
    val name: String = "",
    val photoUrl: String? = null,
    val rating: String = "",
    val price: String = "",
    val description: String = "",
    val isInLibrary: Boolean = false,
    val isLoading: Boolean = true,
    val errorState: GamePageViewModelError = GamePageViewModelError.NoError,
    val steamId: String = "",
    val dealId: String = ""
)