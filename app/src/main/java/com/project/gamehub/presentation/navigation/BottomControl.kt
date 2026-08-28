package com.project.gamehub.presentation.navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
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
        BoxWithConstraints(Modifier.fillMaxSize()) {

            val cellWidth = maxWidth / 2
            val indicatorX by animateDpAsState(
                targetValue = if (curPage is ScreenTypes.MainScreen) 0.dp else cellWidth,
                label = "indicator"
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    IconButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = onGoToSearch,
                        enabled = curPage is ScreenTypes.MyLibrary
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
                        onClick = onGoToLibrary,
                        enabled = curPage is ScreenTypes.MainScreen
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalLibrary,
                            contentDescription = "MyLibrary",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .offset(x = indicatorX)
                    .width(cellWidth)
                    .fillMaxHeight()
                    .padding()
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.White.copy(alpha = 0.2f))

            )

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