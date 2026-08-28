package com.project.gamehub.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp


@Composable
fun BottomControl(
    modifier: Modifier = Modifier,
    curPage: ScreenTypes = ScreenTypes.MainScreen,
    onGoToLibrary: () -> Unit = {},
    onGoToSearch: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .padding(10.dp)
            .navigationBarsPadding(),
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(25.dp)
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
                    onClick = if(curPage is ScreenTypes.MainScreen) { {} } else onGoToSearch
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
                    onClick = if(curPage is ScreenTypes.MyLibrary) { {} } else onGoToLibrary
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

@Preview
@Composable
private fun BottomControlPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            BottomControl(
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .fillMaxHeight(0.09f),
                curPage = ScreenTypes.MyLibrary
            )
        }

    }
}