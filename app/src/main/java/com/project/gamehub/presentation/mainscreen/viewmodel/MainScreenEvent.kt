package com.project.gamehub.presentation.mainscreen.viewmodel

sealed class MainScreenEvent {
    data object NoInternetToast : MainScreenEvent()
}