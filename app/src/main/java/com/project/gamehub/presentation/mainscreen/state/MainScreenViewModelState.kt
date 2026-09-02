package com.project.gamehub.presentation.mainscreen.state

import androidx.compose.runtime.Immutable
import com.project.gamehub.domain.model.GameShortInfo
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenViewModelError

@Immutable
data class MainScreenViewModelState(
    val gamesList: List<GameShortInfo>,
    val pages: Int = 0,
    val searched: List<GameShortInfo> = emptyList(),
    val query: String = "",
    val error: MainScreenViewModelError = MainScreenViewModelError.NoError
)