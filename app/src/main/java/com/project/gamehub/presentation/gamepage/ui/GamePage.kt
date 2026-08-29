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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.project.gamehub.presentation.gamepage.state.GamePageViewModelState
import com.project.gamehub.R

@Composable
fun GamePageRoot(
    gameId: String,
    onBack: () -> Unit
) {

    val state by remember {
        mutableStateOf(
            GamePageViewModelState(
                "game $gameId",
                "https://cs13.pikabu.ru/post_img/2021/04/13/5/og_og_1618296476296911089.jpg",
                5.0,
                "1000",
                "Description of the game. Game is insanely interesting"
            )
        )
    }
    GamePage(state = state, onBack = onBack)
}

@Composable
fun GamePage(
    state: GamePageViewModelState, onBack: () -> Unit = {}
) {

    val scrollState = rememberScrollState(0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .systemBarsPadding()
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
                    IconButton(
                        modifier = Modifier
                            .fillMaxSize(0.75f)
                            .aspectRatio(1f)
                            .align(Alignment.Center),
                        onClick = onBack,
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = "Back",
                        )
                    }
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = state.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

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
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Star"
                    )
                }
            }
            if (state.photoUrl != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = MaterialTheme.colorScheme.secondary)
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
                Spacer(Modifier.padding(10.dp))
            }

            Text(
                text = stringResource(R.string.description),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                state.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(R.string.rating, state.rating),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.price, state.price),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Preview
@Composable
private fun GamePagePreview() {
    MaterialTheme {
        GamePage(
            state = GamePageViewModelState(
                "game",
                null,
                5.0,
                "1000",
                "Description of the game. Game is insanely interesting",
                true
            )
        )
    }
}