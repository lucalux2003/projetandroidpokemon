package com.projet.projetandroidpokemon.ui.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projet.projetandroidpokemon.domain.api.FirebaseDSRC
import com.projet.projetandroidpokemon.domain.database.PokemonCardDataBase
import com.projet.projetandroidpokemon.model.User
import kotlinx.coroutines.launch
import java.security.MessageDigest

class RegisterViewModel : ViewModel() {

    private val userDAO = PokemonCardDataBase.getInstance().userDAO()
    private val _registrationStatus = MutableLiveData<String>()
    val registrationStatus: LiveData<String> get() = _registrationStatus

    fun registerUser(email: String, username: String, password: String, confirmPassword: String, isConnected: Boolean) {
        viewModelScope.launch {
            if (validateInput(email, username, password, confirmPassword)) {
                val hashedPassword = hashPassword(password)
                val user = User(username, email, hashedPassword)

                if (userDAO.getUserFromMail(email) != null) {
                    _registrationStatus.value = "Cet email est déjà utilisé localement."
                } else if (isConnected && FirebaseDSRC.isEmailInFirebase(email)) {
                    _registrationStatus.value = "Cet email est déjà utilisé dans Firebase."
                } else {
                    if (isConnected) {
                        FirebaseDSRC.addUserToFirebase(user)
                    }
                    saveUserToLocalDB(user)
                    _registrationStatus.value = "Inscription réussie"
                }
            }
        }
    }

    private suspend fun saveUserToLocalDB(user: User) {
        userDAO.insertUser(user)
    }

    private fun validateInput(email: String, username: String, password: String, confirmPassword: String): Boolean {
        return when {
            email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                _registrationStatus.value = "Tous les champs doivent être remplis."
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _registrationStatus.value = "Email incorrect."
                false
            }
            password != confirmPassword -> {
                _registrationStatus.value = "Les mots de passe ne correspondent pas."
                false
            }
            password.length < 6 -> {
                _registrationStatus.value = "Le mot de passe doit contenir au moins 6 caractères."
                false
            }
            else -> true
        }
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
