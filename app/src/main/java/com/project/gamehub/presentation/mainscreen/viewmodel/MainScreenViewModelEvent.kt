package com.project.gamehub.presentation.mainscreen.viewmodel

sealed class MainScreenViewModelEvent {
    data object GetGames : MainScreenViewModelEvent()
}