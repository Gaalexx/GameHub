package com.project.gamehub.data.repository

import android.util.Log
import com.project.gamehub.data.remote.GameInfoAPI
import com.project.gamehub.data.remote.GamesAPI
import com.project.gamehub.domain.model.GameFullInfo
import com.project.gamehub.domain.model.GameShortInfo
import com.project.gamehub.domain.repository.GameRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject


class NoSuchGameException : Throwable()

class GameRepositoryImpl @Inject constructor(
    private val api: GamesAPI, private val gameInfoAPI: GameInfoAPI
) : GameRepository {
    override suspend fun getGames(page: Int): Result<List<GameShortInfo>> {

        try {
            val dtos = api.getGames(limit = 100, page = page)
            return Result.success(buildList {
                dtos.forEach { it ->
                    if(it.steamAppId != null){
                        add(
                            GameShortInfo(
                                gameId = it.gameId,
                                name = it.title,
                                photoUrl = it.photoUrl,
                                dealId = it.dealId
                            )
                        )
                    }
                }
            })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return Result.failure(e)
        }


    }

    override suspend fun getGame(id: String): Result<GameFullInfo> {
        try {
            val game = api.getGame(id)
            if (game.gameInfo.steamAppId == null) {
                return Result.failure(NoSuchGameException())
            }
            val steamGame = gameInfoAPI.getGameInfo(game.gameInfo.steamAppId)
            if (steamGame == null || !steamGame.success) {
                return Result.failure(NoSuchGameException())
            }

            return Result.success(
                GameFullInfo(
                    id = steamGame.data?.steamAppId.toString() ?: game.gameInfo.steamAppId,
                    name = steamGame.data?.name ?: game.gameInfo.name,
                    description = steamGame.data?.description ?: "",
                    photoUrl = steamGame.data?.photoUrl ?: game.gameInfo.photoUrl,
                    rating = game.gameInfo.steamRatingPercent,
                    price = game.cheapestPrice.price ?: game.gameInfo.salePrice
                )
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}