package com.project.gamehub.presentation.mainscreen.state

import androidx.compose.runtime.Immutable
import com.project.gamehub.domain.model.Game
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenViewModelError

@Immutable
data class MainScreenViewModelState(
    val gamesList: List<Game>,
    val pages: Int = 0,
    val error: MainScreenViewModelError = MainScreenViewModelError.NoError
)