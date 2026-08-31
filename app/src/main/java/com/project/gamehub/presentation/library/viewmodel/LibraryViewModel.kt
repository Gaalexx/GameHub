package com.project.gamehub.presentation.library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.gamehub.domain.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repo: GameRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LibraryViewModelState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.observeSavedGamesShort().collect { games ->
                    _state.update {
                        it.copy(
                            games = games
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: LibraryViewModelCommand) {
        when (event) {
            is LibraryViewModelCommand.GetSavedGames -> getSavedGames()
        }
    }

    private fun getSavedGames() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val res = repo.observeSavedGamesShort()

            _state.update {
                it.copy(
                    //games = res,
                    isLoading = false
                )
            }
        }
    }
}