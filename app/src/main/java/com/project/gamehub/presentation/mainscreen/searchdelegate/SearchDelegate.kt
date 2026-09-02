package com.project.gamehub.presentation.mainscreen.searchdelegate

import com.project.gamehub.domain.model.GameShortInfo
import com.project.gamehub.domain.repository.GameRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@Singleton
class SearchDelegate @Inject constructor(
    private val repo: GameRepository,
    private val scope: CoroutineScope
) {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val results =
        _query
            .debounce(300)
            .filter { it.length >= 2 }
            .distinctUntilChanged()
            .flatMapLatest { q ->
                flow {
                    val results = repo.findGame(q).getOrNull()

                    if (results != null) {
                        val set: HashSet<String> = HashSet()
                        val res: MutableList<GameShortInfo> = mutableListOf()
                        results.forEach { it ->
                            if (it.gameId !in set) {
                                set.add(it.gameId)
                                res.add(it)
                            }
                        }
                        emit(res.toList())
                    }
                }
                    .catch { emit(emptyList()) }
            }.stateIn(
                scope, SharingStarted.WhileSubscribed(5000), emptyList()
            )

    fun onQueryChange(value: String) {
        _query.value = value
    }

}