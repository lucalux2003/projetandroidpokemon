package com.projet.projetandroidpokemon.manager

import android.content.Context
import android.util.Log

class UserSessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    fun saveUser(email: String, name: String) {
        prefs.edit().apply {
            putString("USER_EMAIL", email)
            putString("USER_NAME", name)
            putBoolean("IS_LOGGED_IN", true)
            apply()
        }
    }

    fun getUserEmail(): String? {
        val email = prefs.getString("USER_EMAIL", null)
        return email
    }

    fun isUserLoggedIn(): Boolean {
        val loggedIn = prefs.getBoolean("IS_LOGGED_IN", false)
        return loggedIn
    }

    fun getUserName(): String? = prefs.getString("USER_NAME", null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
