package com.projet.projetandroidpokemon.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.projet.projetandroidpokemon.manager.InternetChecker
import com.projet.projetandroidpokemon.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.registrationStatus.observe(this, Observer { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
            if (status == "Inscription réussie") {
                navigateToLoginActivity()
            }
        })

        binding.registerButton.setOnClickListener {
            val email = binding.registerEditEmail.text.toString()
            val username = binding.registerEditUsername.text.toString()
            val password = binding.registerEditPassword.text.toString()
            val confirmPassword = binding.registerEditPasswordConfirmation.text.toString()
            val isConnected = InternetChecker(this).isInternetAvailable()

            viewModel.registerUser(email, username, password, confirmPassword, isConnected)
        }

        binding.registerPageLogin.setOnClickListener {
            navigateToLoginActivity()
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}
