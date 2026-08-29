package com.project.gamehub.data.repository

import com.project.gamehub.data.remote.GameInfoAPI
import com.project.gamehub.data.remote.GamesAPI
import com.project.gamehub.domain.model.GameFullInfo
import com.project.gamehub.domain.model.GameShortInfo
import com.project.gamehub.domain.repository.GameRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject


class NoSuchGameException : Throwable()

class GameRepositoryImpl @Inject constructor(
    private val api: GamesAPI,
    private val gameInfoAPI: GameInfoAPI
) : GameRepository {
    override suspend fun getGames(page: Int): Result<List<GameShortInfo>> {

        try {
            val dtos = api.getGames(page = page)
            return Result.success(buildList {
                dtos.forEach { it ->
                    add(
                        GameShortInfo(
                            gameId = it.gameId, name = it.title, photoUrl = it.photoUrl
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

    override suspend fun getGameBySteamId(steamGameId: String): Result<GameFullInfo> {
        // TODO переделть под просто игру по id с экрана, чтобы получать рейтинг и цену из первого апи
        try{
            val res =
                gameInfoAPI.getGameInfo(steamGameId)

            if(res == null || !res.success){
                return Result.failure(NoSuchGameException())
            }

            return Result.success(
                GameFullInfo(
                    id = steamGameId,
                    name = res.data?.name ?: "",
                    description = res.data?.description ?: "",
                    photoUrl = res.data?.photoUrl ?: "",
                    rating = 0.0,
                    price = 0.0
                )
            )
        }
        catch (e: CancellationException){
            throw e
        }
        catch (e: Exception){
            return Result.failure(e)
        }



    }

}