package com.project.gamehub.presentation.gamepage.viewmodel


sealed class GamePageViewModelError {
    data object NoError : GamePageViewModelError()
    data object NoInternet : GamePageViewModelError()
    data object Unknown : GamePageViewModelError()
}