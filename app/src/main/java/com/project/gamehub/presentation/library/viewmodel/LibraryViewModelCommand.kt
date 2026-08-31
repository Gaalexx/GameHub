package com.project.gamehub.presentation.library.viewmodel

sealed class LibraryViewModelCommand {
    data object GetSavedGames : LibraryViewModelCommand()
}