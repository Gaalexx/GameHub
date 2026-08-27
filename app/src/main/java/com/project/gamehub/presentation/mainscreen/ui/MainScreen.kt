package com.project.gamehub.presentation.mainscreen.ui

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.gamehub.R
import com.project.gamehub.domain.model.Game
import com.project.gamehub.presentation.mainscreen.state.MainScreenViewModelState
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenEvent
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenViewModel
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenViewModelCommand
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenViewModelError
import com.project.gamehub.presentation.theme.GameHubTheme

@Composable
fun MainScreenRoot(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {

    val state = mainScreenViewModel.state.collectAsStateWithLifecycle().value
    val onEvent = mainScreenViewModel::onEvent
    val context = LocalContext.current

    val noInternetMessage = stringResource(R.string.no_internet_error)


    LaunchedEffect(Unit) {
        onEvent(MainScreenViewModelCommand.GetGames)
    }

    LaunchedEffect(Unit) {
        mainScreenViewModel.event.collect { event ->
            when (event) {
                is MainScreenEvent -> {
                    Toast.makeText(context, noInternetMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    MainScreen(
        mainViewModelState = state, onEvent = onEvent
    )
}

@Composable
fun MainScreen(
    mainViewModelState: MainScreenViewModelState,
    onEvent: (MainScreenViewModelCommand) -> Unit
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

        when (mainViewModelState.error) {
            is MainScreenViewModelError.NoError -> {
                GamesGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    games = mainViewModelState.gamesList,
                    onLoadMore = {
                        onEvent(MainScreenViewModelCommand.GetGames)
                    })
            }

            is MainScreenViewModelError.NoInternet -> {
                RetryHolder(
                    modifier = Modifier.fillMaxSize(),
                    whyRetry = stringResource(R.string.error),
                    whatReason = stringResource(R.string.no_internet_error),
                    onRetry = { onEvent(MainScreenViewModelCommand.GetGames) }
                )
            }

            is MainScreenViewModelError.Unknown -> {
                RetryHolder(
                    modifier = Modifier.fillMaxSize(),
                    whyRetry = stringResource(R.string.error),
                    whatReason = stringResource(R.string.unknown_error),
                    onRetry = { onEvent(MainScreenViewModelCommand.GetGames) }
                )
            }
        }


    }
}


@Preview
@Composable
fun MainScreenPreview() {
    GameHubTheme {
        MainScreen(
            MainScreenViewModelState(
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