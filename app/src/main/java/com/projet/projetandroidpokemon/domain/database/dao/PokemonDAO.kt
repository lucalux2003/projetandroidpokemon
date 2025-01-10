package com.projet.projetandroidpokemon.domain.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.projet.projetandroidpokemon.model.PokemonUserCards

@Dao
interface PokemonDAO {

    @Insert
    suspend fun insertPokemon(pokemonCard: PokemonUserCards)

    @Update
    suspend fun updatePokemon(pokemonCard: PokemonUserCards)

    @Delete
    suspend fun deletePokemon(pokemonCard: PokemonUserCards)

    @Query("SELECT * FROM PokemonUserCards WHERE userEmail = :mail")
    suspend fun getPokemonCardsForUser(mail: String): List<PokemonUserCards>

    @Query("SELECT * FROM PokemonUserCards WHERE cardId = :cardId AND userEmail = :mail")
    suspend fun getPokemonById(cardId: String, mail: String): PokemonUserCards?

    @Query("SELECT * FROM PokemonUserCards")
    suspend fun getAllCards(): List<PokemonUserCards>

    @Query("SELECT * FROM PokemonUserCards WHERE userEmail = :userEmail LIMIT :limit OFFSET :offset")
    suspend fun getPokemonCardsForUserPaged(userEmail: String, offset: Int, limit: Int): List<PokemonUserCards>
}
