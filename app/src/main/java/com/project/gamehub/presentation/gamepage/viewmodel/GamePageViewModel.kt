package com.project.gamehub.presentation.gamepage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.gamehub.domain.model.GameFullInfo
import com.project.gamehub.domain.repository.GameRepository
import com.project.gamehub.presentation.gamepage.state.GamePageViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class GamePageViewModel @Inject constructor(
    private val repo: GameRepository
) : ViewModel() {
    private val _state = MutableStateFlow(GamePageViewModelState())
    val state = _state.asStateFlow()

    fun onEvent(event: GamePageViewModelCommand) {
        when (event) {
            is GamePageViewModelCommand.LoadGameInfo -> loadGameInfo(event.dealId)
            is GamePageViewModelCommand.SaveGame -> saveGame()
            is GamePageViewModelCommand.DeleteGame -> deleteGame()
        }
    }

    private fun loadGameInfo(dealId: String) {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    errorState = GamePageViewModelError.NoError
                )
            }

            val info = repo.getGame(dealId)

            if (info.isSuccess) {
                val infoGot = info.getOrNull()!!
                _state.update {
                    it.copy(
                        name = infoGot.name,
                        photoUrl = infoGot.photoUrl,
                        rating = infoGot.rating,
                        price = infoGot.price,
                        description = infoGot.description,
                        isLoading = false,
                        errorState = GamePageViewModelError.NoError,
                        isInLibrary = infoGot.saved,
                        steamId = infoGot.id,
                        dealId = dealId
                    )
                }
            } else {
                when (info.exceptionOrNull()) {
                    is UnknownHostException -> {
                        _state.update {
                            it.copy(
                                errorState = GamePageViewModelError.NoInternet
                            )
                        }
                    }

                    else -> {
                        _state.update {
                            it.copy(
                                errorState = GamePageViewModelError.Unknown
                            )
                        }
                    }
                }
            }
        }
    }

    private fun saveGame() {
        viewModelScope.launch {
            repo.saveGame(
                GameFullInfo(
                    id = _state.value.steamId,
                    name = _state.value.name,
                    description = _state.value.description,
                    photoUrl = _state.value.photoUrl,
                    rating = _state.value.rating,
                    price = _state.value.price,
                ),
                dealId = _state.value.dealId
            )
            _state.update {
                it.copy(
                    isInLibrary = true
                )
            }
        }
    }

    private fun deleteGame() {
        viewModelScope.launch {
            repo.deleteBySteamId(_state.value.steamId)
            _state.update {
                it.copy(
                    isInLibrary = false
                )
            }
        }
    }
}