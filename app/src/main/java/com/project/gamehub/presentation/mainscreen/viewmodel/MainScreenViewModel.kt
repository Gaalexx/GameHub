package com.project.gamehub.presentation.mainscreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.gamehub.domain.model.GameShortInfo
import com.project.gamehub.domain.repository.GameRepository
import com.project.gamehub.presentation.mainscreen.state.MainScreenViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repo: GameRepository
) : ViewModel(){
    companion object{
        const val TAG = "MainScreenViewModel"
    }
    private val usedGameIds: HashSet<String> = HashSet()
    private val _state = MutableStateFlow<MainScreenViewModelState>(MainScreenViewModelState(gamesList = emptyList()))
    val state = _state.asStateFlow()
    private val _event = MutableSharedFlow<MainScreenEvent>()
    val event = _event.asSharedFlow()

    fun onEvent(event: MainScreenViewModelCommand){
        when(event){
            is MainScreenViewModelCommand.GetGames -> getGames()
        }
    }

    private fun getGames(){
        viewModelScope.launch {
            val result = repo.getGames(_state.value.pages + 1)

            if(result.isSuccess){
                val games = result.getOrNull()

                val list: List<GameShortInfo> = buildList{
                    games?.forEach { it ->
                        if(it.gameId !in usedGameIds){
                            usedGameIds.add(it.gameId)
                            add(it)
                        }
                    } ?: emptyList<GameShortInfo>()
                }

                _state.update {
                    it.copy(
                        gamesList = it.gamesList + list,
                        pages = _state.value.pages + 1,
                        error = MainScreenViewModelError.NoError
                    )
                }
            }
            else{
                when(result.exceptionOrNull()){
                    is UnknownHostException -> {
                        if(_state.value.gamesList.isNotEmpty()){
                            _event.emit(MainScreenEvent.NoInternetToast)
                        }
                        else{
                            _state.update {
                                it.copy(
                                    error = MainScreenViewModelError.NoInternet
                                )
                            }
                        }
                    }
                    else -> {
                        _state.update {
                            it.copy(
                                error = MainScreenViewModelError.Unknown
                            )
                        }
                    }
                }
                // TODO обработать ошибки
                Log.e(TAG, "getGames exception: ${result.exceptionOrNull()?.message}")
            }
        }

    }
}