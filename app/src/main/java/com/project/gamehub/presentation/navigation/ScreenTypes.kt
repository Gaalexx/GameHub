package com.project.gamehub.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenTypes : NavKey {
    sealed interface BottomBarNavigatable

    @Serializable
    data object MainScreen : ScreenTypes(), BottomBarNavigatable

    @Serializable
    data object MyLibrary : ScreenTypes(), BottomBarNavigatable

    @Serializable
    data class GameReview(val gameId: String) : ScreenTypes()

}