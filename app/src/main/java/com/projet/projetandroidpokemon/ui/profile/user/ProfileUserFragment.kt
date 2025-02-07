package com.projet.projetandroidpokemon.ui.profile.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.projet.projetandroidpokemon.R
import com.projet.projetandroidpokemon.ui.login.LoginActivity
import com.projet.projetandroidpokemon.ui.profile.achievements.UserAchivementsFragment
import com.projet.projetandroidpokemon.ui.profile.friends.UserFriendsFragment

class ProfileUserFragment : Fragment() {

    private val profileUserViewModel: ProfileUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_profil, container, false)

        val friendsButton: Button = root.findViewById(R.id.friendsButton)
        friendsButton.setOnClickListener {
            val userFriendsFragment = UserFriendsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, userFriendsFragment, null)
                .addToBackStack(null)
                .commit()
        }

        val parametersButton: Button = root.findViewById(R.id.parametersButton)
        parametersButton.setOnClickListener {
            val userSettingsFragment = UserSettingsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, userSettingsFragment, null)
                .addToBackStack(null)
                .commit()
        }

        val achivementsButton: Button = root.findViewById(R.id.AchivementsButton)
        achivementsButton.setOnClickListener {
            val userAchivementsFragment = UserAchivementsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, userAchivementsFragment, null)
                .addToBackStack(null)
                .commit()
        }

        val logoutButton : ImageButton = root.findViewById<ImageButton>(R.id.logoutButton)

        logoutButton.setOnClickListener{
            profileUserViewModel.logoutUser()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameText: TextView = view.findViewById(R.id.usernameText)

        profileUserViewModel.username.observe(viewLifecycleOwner) { username ->
            usernameText.text = username ?: "Username not valid"
        }
    }
}
