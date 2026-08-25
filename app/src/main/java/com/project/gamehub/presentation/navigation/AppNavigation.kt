package com.project.gamehub.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.project.gamehub.presentation.mainscreen.ui.MainScreenRoot

@Composable
fun AppNavigation(){
    val navBackStack = rememberNavBackStack(ScreenTypes.MainScreen)
    val navEntryProvider = entryProvider<NavKey> {
        entry<ScreenTypes.MainScreen> {
            MainScreenRoot()
        }
    }

    NavDisplay(
        backStack = navBackStack,
        onBack = { navBackStack.removeLastOrNull() },
        entryProvider = navEntryProvider
    )

}