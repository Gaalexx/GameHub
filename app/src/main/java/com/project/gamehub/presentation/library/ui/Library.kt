package com.project.gamehub.presentation.library.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.gamehub.domain.model.GameShortInfo
import com.project.gamehub.presentation.library.viewmodel.LibraryViewModel
import com.project.gamehub.presentation.library.viewmodel.LibraryViewModelCommand
import com.project.gamehub.presentation.library.viewmodel.LibraryViewModelState
import com.project.gamehub.presentation.shared.GamesGrid


@Composable
fun LibraryRoot(
    navigateToGame: (GameShortInfo) -> Unit,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onEvent = viewModel::onEvent
    LaunchedEffect(Unit) {
        onEvent(LibraryViewModelCommand.GetSavedGames)
    }

    Library(
        state = state,
        navigateToGame = navigateToGame
    )
}

@Composable
fun Library(
    state: LibraryViewModelState,
    navigateToGame: (GameShortInfo) -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .systemBarsPadding(),
    ) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            GamesGrid(
                modifier = Modifier.fillMaxSize(),
                games = state.games,
                onLoadMore = {},
                navigateToGame = navigateToGame
            )
        }

    }
}
