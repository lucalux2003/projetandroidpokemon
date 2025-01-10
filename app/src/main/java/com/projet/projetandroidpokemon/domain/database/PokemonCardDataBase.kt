package com.projet.projetandroidpokemon.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.projet.projetandroidpokemon.domain.database.dao.PokemonDAO
import com.projet.projetandroidpokemon.domain.database.dao.UserDAO
import com.projet.projetandroidpokemon.model.Converters
import com.projet.projetandroidpokemon.model.PokemonUserCards
import com.projet.projetandroidpokemon.model.User

@Database(entities = [User::class, PokemonUserCards::class], version = 2)
@TypeConverters(Converters::class)
abstract class PokemonCardDataBase: RoomDatabase() {

	abstract fun userDAO(): UserDAO
	abstract fun PokemonDAO(): PokemonDAO

	companion object {

		private lateinit var instance: PokemonCardDataBase

		fun initDatabase(context: Context) {
			instance = Room.databaseBuilder(
				context, PokemonCardDataBase::class.java,
				"pokemon-db"
			).fallbackToDestructiveMigration()
				.build()
		}

		fun getInstance(): PokemonCardDataBase {
			return instance
		}
	}

}