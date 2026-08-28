package com.project.gamehub.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenTypes : NavKey
{
    @Serializable
    data object MainScreen : ScreenTypes()
    @Serializable
    data class GameReview(val gameUri: String) : ScreenTypes()
    @Serializable
    data object MyLibrary : ScreenTypes()
}