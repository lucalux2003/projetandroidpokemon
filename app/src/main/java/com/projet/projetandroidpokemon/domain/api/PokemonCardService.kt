package com.projet.projetandroidpokemon.domain.api

import retrofit2.http.Query
import com.projet.projetandroidpokemon.model.PokemonCard
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonCardService {

	@GET("cards")
	suspend fun getCards(
		@Query("page") page: Int,
		@Query("pageSize") pageSize: Int,
		@Query("orderBy") orderBy: String? = null
	): Response<PokemonCardsResponse>

	@GET("cards")
	suspend fun getFilteredAndOrderedCards(
		@Query("page") page: Int,
		@Query("pageSize") pageSize: Int,
		@Query("type") type: String? = null,
		@Query("rarity") rarity: String? = null,
		@Query("bonus") bonus: String? = null,
		@Query("orderBy") orderBy: String? = null
	): Response<PokemonCardsResponse>


	@GET("cards")
	suspend fun getCardsByRarity(
		@Query("rarity") rarity: String,
		@Query("page") page: Int,
		@Query("pageSize") pageSize: Int
	): Response<PokemonCardsResponse>

	@GET("cards/{id}")
	suspend fun getCardById(@Path("id") cardId: String): Response<PokemonCardResponse>

}

data class PokemonCardResponse(
	val data: PokemonCard
)

data class PokemonCardsResponse(
	val data: List<PokemonCard>
)


