package com.projet.projetandroidpokemon.manager

import android.content.Context

class UserSessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    fun saveUser(email: String, name: String) {
        prefs.edit().apply {
            putString("USER_EMAIL", email)
            putString("USER_NAME", name)
            apply()
        }
    }

    fun getUserEmail(): String? = prefs.getString("USER_EMAIL", null)
    fun getUserName(): String? = prefs.getString("USER_NAME", null)
    fun clearSession() = prefs.edit().clear().apply()
}