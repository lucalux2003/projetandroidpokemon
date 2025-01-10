package com.projet.projetandroidpokemon.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projet.projetandroidpokemon.UserSessionManager

class ProfileUserViewModel(application: Application) : AndroidViewModel(application) {

    private val userSessionManager: UserSessionManager = UserSessionManager(application)

    private val _username = MutableLiveData<String>().apply {
        value = userSessionManager.getUserName() // Retrieve username or default
    }
    val username: LiveData<String> = _username

}