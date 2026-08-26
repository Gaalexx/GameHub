package com.project.gamehub.presentation.mainscreen.state

import androidx.compose.runtime.Immutable
import com.project.gamehub.domain.model.Game

@Immutable
data class MainViewModelState(
    val gamesList: List<Game>,
    val pages: Int = 0
)