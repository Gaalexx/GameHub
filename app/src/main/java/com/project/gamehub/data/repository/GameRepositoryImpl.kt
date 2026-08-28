package com.project.gamehub.data.repository

import com.project.gamehub.data.remote.GamesAPI
import com.project.gamehub.domain.model.GameFullInfo
import com.project.gamehub.domain.model.GameShortInfo
import com.project.gamehub.domain.repository.GameRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val api: GamesAPI
) : GameRepository {
    override suspend fun getGames(page: Int): Result<List<GameShortInfo>> {

        try {
            val dtos = api.getGames(page = page)
            return Result.success(buildList {
                dtos.forEach { it ->
                    add(
                        GameShortInfo(
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

    override suspend fun getGame(page: Int): Result<GameFullInfo> {
        TODO("Not yet implemented")
    }

}