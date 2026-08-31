package com.project.gamehub.presentation.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.project.gamehub.R

@Composable
fun ConnectionErrorScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    RetryHolder(
        modifier = modifier,
        whyRetry = stringResource(R.string.error),
        whatReason = stringResource(R.string.no_internet_error),
        onRetry = onClick
    )
}

@Composable
fun UnknownErrorScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    RetryHolder(
        modifier = modifier,
        whyRetry = stringResource(R.string.error),
        whatReason = stringResource(R.string.unknown_error),
        onRetry = onClick
    )
}