package com.project.gamehub.presentation.gamepage.viewmodel

sealed class GamePageViewModelCommand {
    data class LoadGameInfo(val dealId: String) : GamePageViewModelCommand()
}