package com.project.gamehub.presentation.library.viewmodel

import androidx.compose.runtime.Immutable
import com.project.gamehub.domain.model.GameFullInfo
import com.project.gamehub.domain.model.GameShortInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Immutable
data class LibraryViewModelState(
    val games: List<GameShortInfo> = listOf(),
    val isLoading: Boolean = true
)
