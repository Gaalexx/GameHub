package com.project.gamehub.data.repository

import com.project.gamehub.data.remote.GamesAPI
import com.project.gamehub.domain.model.Game
import com.project.gamehub.domain.repository.GameRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val api: GamesAPI
) : GameRepository {
    override suspend fun getGames(page: Int): Result<List<Game>> {

        try {
            val dtos = api.getGames(page = page)
            return Result.success(buildList {
                dtos.forEach { it ->
                    add(
                        Game(
                            gameId = it.gameId, name = it.title,
                            //description = it.description ?: "No description",
                            photoUrl = it.photoUrl
                        )
                    )
                }
            })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return Result.failure(e)
        }


    }

}