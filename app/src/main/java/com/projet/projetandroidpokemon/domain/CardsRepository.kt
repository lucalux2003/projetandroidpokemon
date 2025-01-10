package com.projet.projetandroidpokemon.domain

import android.util.Log
import com.projet.projetandroidpokemon.domain.api.NetworkDataSource
import com.projet.projetandroidpokemon.domain.api.PokemonCardResponse
import com.projet.projetandroidpokemon.domain.database.PokemonCardDataBase
import com.projet.projetandroidpokemon.model.PokemonCard
import com.projet.projetandroidpokemon.model.PokemonUserCards
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import kotlin.random.Random

object CardsRepository {

	suspend fun getCards(page: Int, pageSize: Int): Flow<Result<List<PokemonCard>>> = flow {
		try {
			val response = NetworkDataSource.apiService.getCards(page, pageSize)
			if (response.isSuccessful) {
				val cards = response.body()?.data
				if (cards != null) {
					emit(Result.success(cards))
				} else {
					emit(Result.failure(Exception("No data found")))
				}
			} else {
				emit(Result.failure(Exception("Error: ${response.code()}")))
			}
		} catch (e: Exception) {
			emit(Result.failure(e))
		}
	}

    private val rarityGroups: Map<String, Int> = mapOf(
        "Common" to 5000,
        "Uncommon" to 2500,
        "Rare" to 1000,
        "Amazing Rare" to 500,
        "Promo" to 300,
        "Rare ACE" to 200,
        "Rare BREAK" to 100,
        "Rare Holo" to 100,
        "Rare Holo EX" to 50,
        "Rare Holo GX" to 50,
        "Rare Holo LV.X" to 50,
        "Rare Holo Star" to 50,
        "Rare Holo V" to 50,
        "Rare Holo VMAX" to 50,
        "Rare Prime" to 50,
        "Rare Prism Star" to 50,
        "Rare Shiny" to 50,
        "Rare Shiny GX" to 50,
        "Rare Ultra" to 20,
        "Rare Rainbow" to 20,
        "Rare Secret" to 10,
        "Rare Shining" to 10,
        "LEGEND" to 5
    )



    private fun getRandomRarity(): String {
        val totalWeight = rarityGroups.values.sumOf { it }
        val randomValue = Random.nextInt(totalWeight)

        var cumulativeProbability = 0
        for ((rarity, probability) in rarityGroups) {
            cumulativeProbability += probability
            if (randomValue < cumulativeProbability) {
                return rarity
            }
        }
        return "Common"
    }



    suspend fun drawRandomCards(count: Int): Flow<Result<List<PokemonCard>>> = flow {
        val drawnCards = mutableListOf<PokemonCard>()

        repeat(count) {
            var rarity: String
            var validCards: List<PokemonCard> = emptyList()

            while (validCards.isEmpty()) {
                rarity = getRandomRarity()
                val response = NetworkDataSource.apiService.getCardsByRarity(rarity, page = (1..741).random(), pageSize = 25) // On prend une page avec 25 cartes

                if (response.isSuccessful) {
                    val cards = response.body()?.data
                    if (cards != null && cards.isNotEmpty()) {
                        validCards = cards.filter { it.rarity == rarity }

                        Log.e("rarity", "drawRandomCards: $rarity, cards found: ${validCards.size}")
                    }
                }

                if (validCards.isEmpty()) {
                    Log.e("rarity", "No valid cards found for rarity $rarity. Rerolling the batch of cards...")
                }
            }

            val randomCard = validCards.random()
            drawnCards.add(randomCard)
        }

        emit(Result.success(drawnCards))
    }


    suspend fun getCardsFromDatabase(userEmail: String, page: Int = 1, pageSize: Int = 20): Flow<Result<List<PokemonUserCards>>> = flow {
        try {
            val offset = (page - 1) * pageSize
            val limit = pageSize

            val cards = PokemonCardDataBase.getInstance().PokemonDAO().getPokemonCardsForUserPaged(userEmail, offset, limit)

            if (cards.isNotEmpty()) {
                emit(Result.success(cards))
            } else {
                emit(Result.failure(Exception("No cards found in database for page $page")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }


    suspend fun getCardById(cardId: String): Response<PokemonCardResponse> {
		return NetworkDataSource.apiService.getCardById(cardId)
	}

    suspend fun getCardsOrderBy(page: Int, pageSize: Int, orderBy: String? = null): Flow<Result<List<PokemonCard>>> = flow {
        try {
            val response = NetworkDataSource.apiService.getCards(page, pageSize, orderBy)
            if (response.isSuccessful) {
                val cards = response.body()?.data
                if (cards != null) {
                    emit(Result.success(cards))
                } else {
                    emit(Result.failure(Exception("No data found")))
                }
            } else {
                emit(Result.failure(Exception("Error: ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun getFilteredAndOrderedCards(
        page: Int,
        pageSize: Int,
        type: String?,
        rarity: String?,
        bonus: String?,
        orderBy: String?
    ): Flow<Result<List<PokemonCard>>> = flow {
        try {
            val response = NetworkDataSource.apiService.getFilteredAndOrderedCards(
                page, pageSize, type, rarity, bonus, orderBy
            )
            if (response.isSuccessful) {
                val cards = response.body()?.data
                if (cards != null) {
                    emit(Result.success(cards))
                } else {
                    emit(Result.failure(Exception("No data found")))
                }
            } else {
                emit(Result.failure(Exception("Error: ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }


}