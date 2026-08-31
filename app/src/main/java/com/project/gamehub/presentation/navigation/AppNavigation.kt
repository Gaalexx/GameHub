package com.project.gamehub.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.project.gamehub.presentation.gamepage.ui.GamePageRoot
import com.project.gamehub.presentation.library.ui.Library
import com.project.gamehub.presentation.library.ui.LibraryRoot
import com.project.gamehub.presentation.mainscreen.ui.MainScreenRoot

@Composable
fun AppNavigation() {
    val navBackStack = rememberNavBackStack(ScreenTypes.MainScreen)
    val navEntryProvider = entryProvider<NavKey> {
        entry<ScreenTypes.MainScreen> {
            MainScreenRoot(
                navigateToGame = { it ->
                    navBackStack.add(ScreenTypes.GameReview(it))
                })
        }
        entry<ScreenTypes.MyLibrary> {
            LibraryRoot(
                navigateToGame = { it ->
                    navBackStack.add(ScreenTypes.GameReview(it))
                }
            )
        }
        entry<ScreenTypes.GameReview> { it ->
            GamePageRoot(game = it.game, onBack = { navBackStack.removeLastOrNull() })
        }
    }

    val curPage = navBackStack.lastOrNull()

    NavDisplay(
        backStack = navBackStack,
        onBack = { navBackStack.removeLastOrNull() },
        entryProvider = navEntryProvider
    )

    if (navBackStack.last() is ScreenTypes.BottomBarNavigatable) {
        Box(modifier = Modifier.fillMaxSize()) {
            BottomControl(
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .height(100.dp)
                    .fillMaxWidth(0.8f),
                curPage = curPage as? ScreenTypes ?: ScreenTypes.MainScreen,
                onGoToSearch = { navBackStack.removeLastOrNull() },
                onGoToLibrary = { navBackStack.add(ScreenTypes.MyLibrary) })
        }
    }
}