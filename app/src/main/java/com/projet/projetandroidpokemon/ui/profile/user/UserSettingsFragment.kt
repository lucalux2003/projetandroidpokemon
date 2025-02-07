package com.projet.projetandroidpokemon.ui.profile.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.projet.projetandroidpokemon.R

class UserSettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_settings, container, false)

        val returnButton: ImageButton = root.findViewById<ImageButton>(R.id.backButton)
        returnButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return root
    }
}