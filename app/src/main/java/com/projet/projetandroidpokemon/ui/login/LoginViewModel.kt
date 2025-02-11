package com.projet.projetandroidpokemon.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.projet.projetandroidpokemon.manager.UserSessionManager
import com.projet.projetandroidpokemon.domain.api.FirebaseDSRC
import com.projet.projetandroidpokemon.domain.database.PokemonCardDataBase
import com.projet.projetandroidpokemon.domain.database.dao.UserDAO
import com.projet.projetandroidpokemon.model.User
import kotlinx.coroutines.launch
import java.security.MessageDigest

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userDAO: UserDAO = PokemonCardDataBase.getInstance().userDAO()
    private val userSessionManager = UserSessionManager(application)

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    init {
        synchronizeUsersFromFirebase()
    }

    fun authenticateUser(email: String, password: String, isInternetAvailable: Boolean) {
        val hashedPassword = hashPassword(password)

        viewModelScope.launch {
            val localUser = userDAO.getUserFromMail(email)

            if (localUser != null && localUser.password == hashedPassword) {
                userSessionManager.saveUser(email, localUser.name)
                _loginResult.value = true
            } else if (isInternetAvailable) {
                try {
                    val isAuthenticated = FirebaseDSRC.authenticateUser(email, hashedPassword)
                    if (isAuthenticated) {
                        val firebaseUser = FirebaseDSRC.getUserByEmail(email)
                        if (firebaseUser != null) {
                            userDAO.insertUser(firebaseUser)

                            userSessionManager.saveUser(email, firebaseUser.name)
                        }
                    }
                    _loginResult.value = isAuthenticated
                } catch (e: Exception) {
                    Log.e("LoginViewModel", "Firebase auth error: ${e.message}")
                    _loginResult.value = false
                }
            } else {
                _loginResult.value = false
            }
        }
    }

    fun synchronizeUsersFromFirebase() {
        viewModelScope.launch {
            try {
                val users = FirebaseDSRC.getAllUsers()
                users.forEach { user ->
                    val localUser = userDAO.getUserFromMail(user.email)
                    if (localUser == null) {
                        userDAO.insertUser(user)
                    }
                }
                Log.d("LoginViewModel", "Firebase local data.")
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error synchro users Firebase: ${e.message}")
            }
        }
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
