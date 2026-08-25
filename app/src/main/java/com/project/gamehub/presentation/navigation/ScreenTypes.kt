package com.project.gamehub.presentation.navigation

import androidx.navigation3.runtime.NavKey

sealed class ScreenTypes : NavKey
{
    data object MainScreen : ScreenTypes()
    data class GameReview(val gameUri: String) : ScreenTypes()
    data object MyLibrary : ScreenTypes()
}