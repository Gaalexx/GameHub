package com.project.gamehub.presentation.mainscreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.gamehub.domain.model.Game
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
    companion object{
        const val TAG = "MainScreenViewModel"
    }
    private val usedGameIds: HashSet<String> = HashSet()
    private val _state = MutableStateFlow<MainViewModelState>(MainViewModelState(gamesList = emptyList()))
    val state = _state.asStateFlow()

    fun onEvent(event: MainScreenViewModelEvent){
        when(event){
            is MainScreenViewModelEvent.GetGames -> getGames()
        }
    }

    private fun getGames(){
        viewModelScope.launch {
            val result = repo.getGames(_state.value.pages + 1)

            if(result.isSuccess){
                val games = result.getOrNull()

                val list: List<Game> = buildList{
                    games?.forEach { it ->
                        if(it.gameId !in usedGameIds){
                            usedGameIds.add(it.gameId)
                            add(it)
                        }
                    } ?: emptyList<Game>()
                }

                _state.update {
                    it.copy(
                        gamesList = it.gamesList + list,
                        pages = _state.value.pages + 1
                    )
                }
            }
            else{
                // TODO обработать ошибки
                Log.e(TAG, "getGames exception: ${result.exceptionOrNull()?.message}")
            }
        }

    }
}