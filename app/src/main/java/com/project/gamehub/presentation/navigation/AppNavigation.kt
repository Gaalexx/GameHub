package com.project.gamehub.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.project.gamehub.presentation.mainscreen.MainScreenRoot

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