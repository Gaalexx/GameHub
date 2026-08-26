package com.project.gamehub.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.project.gamehub.presentation.library.ui.Library
import com.project.gamehub.presentation.mainscreen.ui.MainScreenRoot

@Composable
fun AppNavigation() {
    val navBackStack = rememberNavBackStack(ScreenTypes.MainScreen)
    val navEntryProvider = entryProvider<NavKey> {
        entry<ScreenTypes.MainScreen> {
            MainScreenRoot()
        }
        entry<ScreenTypes.MyLibrary>{
            Library()
        }
    }

    val curPage = navBackStack.lastOrNull()

    NavDisplay(
        backStack = navBackStack,
        onBack = { navBackStack.removeLastOrNull() },
        entryProvider = navEntryProvider
    )
    Box(modifier = Modifier.fillMaxSize()) {
        BottomControl(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .fillMaxHeight(0.09f),
            curPage = curPage as? ScreenTypes ?: ScreenTypes.MainScreen,
            onGoToSearch = { navBackStack.removeLastOrNull() },
            onGoToLibrary = { navBackStack.add(ScreenTypes.MyLibrary) })
    }


}