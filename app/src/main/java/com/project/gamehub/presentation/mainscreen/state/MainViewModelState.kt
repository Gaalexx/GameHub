package com.project.gamehub.presentation.mainscreen.state

import com.project.gamehub.domain.model.Game

data class MainViewModelState(
    val gamesList: List<Game>,
    val pages: Int = 0
)