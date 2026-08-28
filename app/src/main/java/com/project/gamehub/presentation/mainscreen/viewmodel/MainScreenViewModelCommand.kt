package com.project.gamehub.presentation.mainscreen.viewmodel

sealed class MainScreenViewModelCommand {
    data object GetGames : MainScreenViewModelCommand()
}