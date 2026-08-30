package com.project.gamehub.presentation.gamepage.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.project.gamehub.R
import com.project.gamehub.domain.model.GameShortInfo
import com.project.gamehub.presentation.gamepage.state.GamePageViewModelState
import com.project.gamehub.presentation.gamepage.viewmodel.GamePageViewModel
import com.project.gamehub.presentation.gamepage.viewmodel.GamePageViewModelCommand
import com.project.gamehub.presentation.theme.GameHubTheme

@Composable
fun GamePageRoot(
    game: GameShortInfo,
    onBack: () -> Unit,
    viewModel: GamePageViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onEvent = viewModel::onEvent

    LaunchedEffect(Unit) {
        onEvent(GamePageViewModelCommand.LoadGameInfo(game.dealId!!))
    }

    GamePage(state = state, onBack = onBack)
}

@Composable
fun GamePage(
    state: GamePageViewModelState, onBack: () -> Unit = {}
) {

    val scrollState = rememberScrollState(0)
    val descTextModifier = Modifier.padding(5.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    )
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(
                    scrollState,
                    orientation = Orientation.Vertical
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxHeight()
                ) {
                    FilledTonalIconButton(
                        modifier = Modifier
                            .fillMaxSize(0.75f)
                            .aspectRatio(1f)
                            .align(Alignment.Center),
                        onClick = onBack,
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                        )
                    }
                }
                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxHeight()
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize(0.65f)
                            .align(Alignment.Center),
                        imageVector = if (state.isInLibrary) Icons.Default.Star else Icons.Default.StarOutline,
                        tint = if (state.isInLibrary) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        contentDescription = "Star"
                    )
                }
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

            } else {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = state.name,
                    style = MaterialTheme.typography.headlineSmall,
                )
                if (state.photoUrl != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = MaterialTheme.colorScheme.surfaceContainerHighest)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxWidth(),
                            model = state.photoUrl,
                            contentDescription = "Game photo",
                            contentScale = ContentScale.FillWidth,
                            onError = { it ->
                                Log.e("IMAGE", "ERROR ${it.result.throwable.message}")
                            })
                    }
                    Spacer(Modifier.padding(5.dp))
                }

                Row {
                    Text(
                        modifier = descTextModifier.weight(1f),
                        text = stringResource(R.string.rating, state.rating),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        modifier = descTextModifier.weight(1f),
                        text = stringResource(R.string.price, state.price),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Text(
                    modifier = descTextModifier,
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = descTextModifier,
                    text = state.description,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textIndent = TextIndent(
                            firstLine =  24.sp
                        )
                    ),
                )


            }

        }
    }
}


@Preview
@Composable
private fun GamePagePreview() {
    GameHubTheme {
        GamePage(
            state = GamePageViewModelState(
                "game",
                null,
                "5.0",
                "1000",
                "Description of the game. Game is insanely interesting",
                true,
                false
            )
        )
    }
}
