package com.projet.projetandroidpokemon.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.projet.projetandroidpokemon.manager.InternetChecker
import com.projet.projetandroidpokemon.databinding.ActivityLoginBinding
import com.projet.projetandroidpokemon.ui.init.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (InternetChecker(this).isInternetAvailable()) {
            loginViewModel.synchronizeUsersFromFirebase()
        }

        loginViewModel.loginResult.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                navigateToMainActivity()
            } else {
                Toast.makeText(this, "Échec de l'authentification", Toast.LENGTH_SHORT).show()
            }
        })

        binding.loginButton.setOnClickListener {
            val email = binding.loginEditMail.text.toString()
            val password = binding.loginEditPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.authenticateUser(email, password, InternetChecker(this).isInternetAvailable())
            } else {
                Toast.makeText(this, "Email ou mot de passe vide.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginPageRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
