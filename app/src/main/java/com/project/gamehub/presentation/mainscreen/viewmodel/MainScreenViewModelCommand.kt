package com.project.gamehub.presentation.mainscreen.viewmodel

sealed class MainScreenViewModelCommand {
    data object GetGames : MainScreenViewModelCommand()
    data class OnQueryChange(val query: String) : MainScreenViewModelCommand()
}