package com.project.gamehub.presentation.mainscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.gamehub.R
import com.project.gamehub.presentation.mainscreen.viewmodel.MainScreenViewModelCommand

@Composable
fun RetryHolder(
    modifier: Modifier = Modifier,
    whyRetry: String,
    whatReason: String,
    onRetry: () -> Unit
){
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = whyRetry,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = whatReason,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
            )
            Button(onClick = onRetry) {
                Text(stringResource(R.string.repeat))
            }
        }

    }
}
