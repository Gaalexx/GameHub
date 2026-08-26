package com.project.gamehub.presentation.mainscreen.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.project.gamehub.domain.model.Game


@Composable
fun Card(
    modifier: Modifier = Modifier,
    game: Game
) {
    Surface(
        modifier = modifier
            .padding(5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            if (game.photoUrl != null) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = game.photoUrl,
                    contentDescription = game.name,
                    contentScale = ContentScale.Crop,
                    onError = { it ->
                        Log.e("IMAGE", "ERROR ${it.result.throwable.message}")
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.Black.copy(alpha = 0.75f))
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = game.name,
                    color = Color.White,
                    maxLines = 1
                )
            }

        }
    }
}

@Composable
fun GamesGrid(
    modifier: Modifier = Modifier,
    games: List<Game> = listOf<Game>(),
    gridScrollState: LazyGridState = rememberLazyGridState(),
    onLoadMore: () -> Unit
) {

    val shouldLoadMore by remember {
        derivedStateOf {
            val info = gridScrollState.layoutInfo
            val lastVisibleIndex =
                info.visibleItemsInfo.lastOrNull()?.index ?: -1

            lastVisibleIndex >= info.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadMore()
        }
    }


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        state = gridScrollState,
        contentPadding = PaddingValues(5.dp),
    ) {
        itemsIndexed(
            items = games,
            key = { _, game ->
                "game-id-${game.gameId}"
            },
            contentType = { _, game ->
                "game"
            }
        ) { index, item ->
            Card(
                game = item
            )
        }
    }
}