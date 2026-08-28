package com.project.gamehub.presentation.mainscreen.viewmodel

sealed class MainScreenViewModelError {
    data object NoError : MainScreenViewModelError()
    data object NoInternet : MainScreenViewModelError()
    data object Unknown : MainScreenViewModelError()
}