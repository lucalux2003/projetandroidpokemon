package com.projet.projetandroidpokemon.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
	val name: String,
	@PrimaryKey val email: String,
	val password: String
){
	constructor() : this("", "", "")
}
