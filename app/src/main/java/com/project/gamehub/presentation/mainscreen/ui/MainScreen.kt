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
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    mainViewModelState: MainScreenViewModelState, onEvent: (MainScreenViewModelCommand) -> Unit
) {

    var query by remember { mutableStateOf<String>("") }
    val focusManager = LocalFocusManager.current
    var textFieldHeightDp by remember { mutableStateOf(64.dp) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary)
    ) {

        when (mainViewModelState.error) {
            is MainScreenViewModelError.NoError -> {
                GamesGrid(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = textFieldHeightDp),
                    games = mainViewModelState.gamesList,
                    onLoadMore = {
                        onEvent(MainScreenViewModelCommand.GetGames)
                    })


                TextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text(stringResource(R.string.input_game)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.13f)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                        )
                        .align(Alignment.TopCenter)
                        .onGloballyPositioned{ coordinates ->
                            textFieldHeightDp = (coordinates.positionInWindow().y).dp // TODO додумать как правильно посчитать паддинг, работает некорректно
                        },
                    shape = RoundedCornerShape(25.dp)
                )
            }

            is MainScreenViewModelError.NoInternet -> {
                RetryHolder(
                    modifier = Modifier.fillMaxSize(),
                    whyRetry = stringResource(R.string.error),
                    whatReason = stringResource(R.string.no_internet_error),
                    onRetry = { onEvent(MainScreenViewModelCommand.GetGames) })
            }

            is MainScreenViewModelError.Unknown -> {
                RetryHolder(
                    modifier = Modifier.fillMaxSize(),
                    whyRetry = stringResource(R.string.error),
                    whatReason = stringResource(R.string.unknown_error),
                    onRetry = { onEvent(MainScreenViewModelCommand.GetGames) })
            }
        }


    }


//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = MaterialTheme.colorScheme.secondary)
//            //.systemBars
//    ) {
//
//
//    }
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