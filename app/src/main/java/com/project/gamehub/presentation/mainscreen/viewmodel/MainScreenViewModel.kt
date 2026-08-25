package com.project.gamehub.presentation.mainscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.gamehub.domain.repository.GameRepository
import com.project.gamehub.presentation.mainscreen.state.MainViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repo: GameRepository
) : ViewModel(){
    private val _state = MutableStateFlow<MainViewModelState>(MainViewModelState(gamesList = emptyList()))
    val state = _state.asStateFlow()

    fun onEvent(event: MainScreenViewModelEvent){
        when(event){
            is MainScreenViewModelEvent.GetGames -> getGames()
        }
    }

    private fun getGames(){
        viewModelScope.launch {
            val games = repo.getGames(_state.value.pages + 1)

            // TODO обработать ошибки

            _state.update { // случай успеха
                it.copy(
                    gamesList = games,
                    pages = _state.value.pages + 1
                )
            }
        }
    }
}