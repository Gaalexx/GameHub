package com.project.gamehub.presentation.gamepage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.gamehub.domain.repository.GameRepository
import com.project.gamehub.presentation.gamepage.state.GamePageViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamePageViewModel @Inject constructor(
    private val repo: GameRepository
): ViewModel() {
    private val _state = MutableStateFlow(GamePageViewModelState())
    val state = _state.asStateFlow()

    fun onEvent(event: GamePageViewModelCommand){
        when(event){
            is GamePageViewModelCommand.LoadGameInfo -> loadGameInfo(event.dealId)
        }
    }

    private fun loadGameInfo(dealId: String){
        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val info = repo.getGame(dealId)

            if(info.isSuccess){
                val infoGot = info.getOrNull()!!
                _state.update {
                    it.copy(
                        name = infoGot.name,
                        photoUrl = infoGot.photoUrl,
                        rating = infoGot.rating,
                        price = infoGot.price,
                        description = infoGot.description,
                        isLoading = false
                    )
                }
            }
            else{
                Log.e("GAME LOAD", info.exceptionOrNull()!!.message.toString())
                // TODO обработать ошибку
            }
        }
    }

}