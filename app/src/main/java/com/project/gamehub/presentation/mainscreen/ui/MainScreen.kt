package com.project.gamehub.presentation.mainscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.gamehub.domain.model.Game
import com.project.gamehub.presentation.mainscreen.state.MainViewModelState
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenViewModel
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenViewModelEvent
import com.project.gamehub.presentation.theme.GameHubTheme

@Composable
fun MainScreenRoot(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {

    val state = mainScreenViewModel.state.collectAsStateWithLifecycle().value
    val onEvent = mainScreenViewModel::onEvent

    LaunchedEffect(Unit) {
        onEvent(MainScreenViewModelEvent.GetGames)
    }

    MainScreen(
        mainViewModelState = state, onEvent = onEvent
    )
}

@Composable
fun MainScreen(
    mainViewModelState: MainViewModelState, onEvent: (MainScreenViewModelEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // заполнить кнопкми
            }
        }

        GamesGrid(
            modifier = Modifier
                .fillMaxSize(),
            games = mainViewModelState.gamesList,
            onLoadMore = {
                onEvent(MainScreenViewModelEvent.GetGames)
            })

    }
}


@Preview
@Composable
fun MainScreenPreview() {
    GameHubTheme {
        MainScreen(
            MainViewModelState(
                gamesList = listOf(
                    Game(" 3"),
                    Game(" "),
                    Game("1 "),
                    Game(" 1"),
                    Game(" "),
                    Game(" "),
                    Game(" 2"),
                    Game(" "),
                    Game(" 4"),
                    Game(" "),
                    Game(" 1"),
                    Game(" "),
                    Game(" "),
                    Game(" "),
                    Game("2 "),
                    Game(" "),
                    Game("3 "),
                    Game(" 2"),
                    Game("3 "),
                    Game(" 4")
                )
            ), {})
    }
}