package com.projet.projetandroidpokemon.ui.profile.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projet.projetandroidpokemon.manager.UserSessionManager

class ProfileUserViewModel(application: Application) : AndroidViewModel(application) {

    private val userSessionManager: UserSessionManager = UserSessionManager(application)

    private val _username = MutableLiveData<String>().apply {
        value = userSessionManager.getUserName()
    }
    val username: LiveData<String> = _username

}