package com.projet.projetandroidpokemon.manager

import android.app.Application
import com.projet.projetandroidpokemon.domain.database.PokemonCardDataBase
import timber.log.Timber

class PokemonApp: Application() {

	override fun onCreate() {
		super.onCreate()

		Timber.plant(Timber.DebugTree())

		PokemonCardDataBase.initDatabase(context = applicationContext)

		val syncManager = SyncManager(this)
		syncManager.syncIfConnected()
	}
}