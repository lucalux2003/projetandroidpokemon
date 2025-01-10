package com.projet.projetandroidpokemon.model

class PokemonCard(
	val name: String,
	val id: String? = null,
	var types: List<String>? = null,
	var rarity: String? = null,
	var supertype: String? = null,
	var series: List<String>? = null,
	var images: CardImages? = null,
)

data class CardImages(
	val small: String,
	val large: String
)

data class set(
	val id: String,
	val name: String,
	val series: String
)