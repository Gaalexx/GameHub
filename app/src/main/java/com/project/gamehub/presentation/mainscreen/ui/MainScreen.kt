package com.project.gamehub.presentation.mainscreen.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.gamehub.R
import com.project.gamehub.domain.model.GameShortInfo
import com.project.gamehub.presentation.mainscreen.state.MainScreenViewModelState
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenEvent
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenViewModel
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenViewModelCommand
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenViewModelError
import com.project.gamehub.presentation.shared.ConnectionErrorScreen
import com.project.gamehub.presentation.shared.GamesGrid
import com.project.gamehub.presentation.shared.UnknownErrorScreen
import com.project.gamehub.presentation.theme.GameHubTheme

@Composable
fun MainScreenRoot(
    navigateToGame: (GameShortInfo) -> Unit = {},
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
        mainViewModelState = state, onEvent = onEvent, navigateToGame = navigateToGame
    )
}

@Composable
fun MainScreen(
    mainViewModelState: MainScreenViewModelState,
    onEvent: (MainScreenViewModelCommand) -> Unit,
    navigateToGame: (GameShortInfo) -> Unit = {},
) {

    var query by remember { mutableStateOf<String>("") }
    var searchBarBottom by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {

        when (mainViewModelState.error) {
            is MainScreenViewModelError.NoError -> {
                GamesGrid(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = searchBarBottom),
                    games = if (mainViewModelState.searched == emptyList<GameShortInfo>() || mainViewModelState.query == "") mainViewModelState.gamesList else mainViewModelState.searched,
                    onLoadMore = {
                        onEvent(MainScreenViewModelCommand.GetGames)
                    },
                    navigateToGame = navigateToGame
                )


                TextField(
                    value = mainViewModelState.query,
                    onValueChange = {
                        onEvent(MainScreenViewModelCommand.OnQueryChange(it))
                    },
                    label = { Text(stringResource(R.string.input_game)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.13f)
                        .align(Alignment.TopCenter)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                        )
                        .onGloballyPositioned { coords ->
                            val bottomPx = coords.positionInParent().y + coords.size.height
                            val newValue = with(density) { bottomPx.toDp() }
                            if (newValue != searchBarBottom) searchBarBottom = newValue
                        },
                    shape = RoundedCornerShape(25.dp)
                )
            }

            is MainScreenViewModelError.NoInternet -> {
                ConnectionErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    onClick = { onEvent(MainScreenViewModelCommand.GetGames) })
            }

            is MainScreenViewModelError.Unknown -> {
                UnknownErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    onClick = { onEvent(MainScreenViewModelCommand.GetGames) })
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
                    GameShortInfo(" 3"),
                    GameShortInfo(" "),
                    GameShortInfo("1 "),
                    GameShortInfo(" 1"),
                    GameShortInfo(" "),
                    GameShortInfo(" "),
                    GameShortInfo(" 2"),
                    GameShortInfo(" "),
                    GameShortInfo(" 4"),
                    GameShortInfo(" "),
                    GameShortInfo(" 1"),
                    GameShortInfo(" "),
                    GameShortInfo(" "),
                    GameShortInfo(" "),
                    GameShortInfo("2 "),
                    GameShortInfo(" "),
                    GameShortInfo("3 "),
                    GameShortInfo(" 2"),
                    GameShortInfo("3 "),
                    GameShortInfo(" 4")
                )
            ), {})
    }
}
