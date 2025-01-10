package com.projet.projetandroidpokemon.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.projet.projetandroidpokemon.R
import com.projet.projetandroidpokemon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_deck, R.id.navigation_draw, R.id.navigation_profile
            )
        )
        navView.setupWithNavController(navController)
    }

    fun setNavigationEnabled(enabled: Boolean) {
        val navView: BottomNavigationView = binding.navView
        for (i in 0 until navView.menu.size()) {
            navView.menu.getItem(i).isEnabled = enabled
        }
    }
}