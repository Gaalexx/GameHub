package com.project.gamehub.presentation.mainscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    MainScreen(
        mainViewModelState = state, onEvent = mainScreenViewModel::onEvent
    )
}

@Composable
fun MainScreen(
    mainViewModelState: MainViewModelState, onEvent: (MainScreenViewModelEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // заполнить кнопкми
            }
        }

        GamesGrid(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.91f),
            games = mainViewModelState.gamesList,
            onLoadMore = {
                onEvent(MainScreenViewModelEvent.GetGames)
            })


        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {},
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "WebScreen",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {},
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalLibrary,
                            contentDescription = "MyLibrary",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    GameHubTheme {
        MainScreen(
            MainViewModelState(
                gamesList = listOf(
                    Game(0),
                    Game(1),
                    Game(2),
                    Game(3),
                    Game(4),
                    Game(5),
                    Game(6),
                    Game(7),
                    Game(8),
                    Game(9),
                    Game(10),
                    Game(11),
                    Game(12),
                    Game(13),
                    Game(14),
                    Game(15),
                    Game(16),
                    Game(17),
                    Game(18),
                    Game(19)
                )
            ), {})
    }
}