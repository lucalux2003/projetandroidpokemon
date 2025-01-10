package com.projet.projetandroidpokemon.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "PokemonUserCards",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["userEmail"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PokemonUserCards(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val cardId: String,
    val pokemonName: String,
    var types: List<String>? = null,
    var rarity: String? = null,
    var supertype: String? = null,
    var series: Set<String>? = null,
    var images: CardImages? = null,
) {
    fun toPokemonCard(): PokemonCard {
        return PokemonCard(
            name = pokemonName,
            id = cardId,
            types = types,
            rarity = rarity,
            supertype = supertype,
            series = series?.toList(),
            images = images
        )
    }
}
