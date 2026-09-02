package com.project.gamehub.data.repository

import com.project.gamehub.data.local.dao.SavedGamesDao
import com.project.gamehub.data.local.entity.GameEntity
import com.project.gamehub.data.remote.GameInfoAPI
import com.project.gamehub.data.remote.GamesAPI
import com.project.gamehub.domain.model.GameFullInfo
import com.project.gamehub.domain.model.GameShortInfo
import com.project.gamehub.domain.repository.GameRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class NoSuchGameException : Throwable()

class GameRepositoryImpl @Inject constructor(
    private val api: GamesAPI,
    private val gameInfoAPI: GameInfoAPI,
    private val savedGames: SavedGamesDao
) : GameRepository {
    override suspend fun getGames(page: Int): Result<List<GameShortInfo>> {

        try {
            val dtos = api.getGames(limit = 100, page = page)
            return Result.success(buildList {
                dtos.forEach { it ->
                    if (it.steamAppId != null) {
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
        val saved = getSavedGameByDealId(id)
        if (saved != null) {
            return Result.success(saved)
        }

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

    override suspend fun findGame(name: String): Result<List<GameShortInfo>> {
        try {
            val res = api.getGamesByName(name)
            return Result.success(buildList {
                res.forEach { it ->
                    if (it.steamAppId != null) {
                        add(
                            GameShortInfo(
                                gameId = it.steamAppId,
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


    override suspend fun getSavedGamesFull(): Flow<List<GameFullInfo>> {
        return savedGames.getGames().map { it ->
            it.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun observeSavedGamesShort(): Flow<List<GameShortInfo>> {
        return savedGames.getGames().map { it ->
            it.map { game ->
                GameShortInfo(
                    gameId = game.steamId,
                    name = game.name,
                    photoUrl = game.imageUrl,
                    dealId = game.dealId
                )
            }
        }
    }

    override suspend fun getSavedGameByDealId(dealId: String): GameFullInfo? {
        return savedGames.getGameByDealId(dealId)?.toDomain()?.copy(saved = true)
    }

    override suspend fun getSavedGameBySteamId(steamId: String): GameFullInfo? {
        return savedGames.getGameBySteamId(steamId)?.toDomain()?.copy(saved = true)
    }

    override suspend fun saveGame(game: GameFullInfo, dealId: String) {
        savedGames.insert(
            GameEntity(
                steamId = game.id,
                dealId = dealId,
                name = game.name,
                imageUrl = game.photoUrl,
                description = game.description,
                price = game.price,
                rating = game.rating
            )
        )
    }

    override suspend fun deleteBySteamId(id: String) {
        savedGames.deleteBySteamId(id)
    }

}