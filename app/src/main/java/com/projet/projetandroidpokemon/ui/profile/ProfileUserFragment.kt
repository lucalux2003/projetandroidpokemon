package com.projet.projetandroidpokemon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.projet.projetandroidpokemon.ui.profile.ProfileUserViewModel
import androidx.fragment.app.viewModels
import com.projet.projetandroidpokemon.ui.profile.UserAchivementsFragment
import com.projet.projetandroidpokemon.ui.profile.UserFriendsFragment
import com.projet.projetandroidpokemon.ui.profile.UserSettingsFragment


/**
 * A simple [Fragment] subclass.
 * Use the [ProfilUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileUserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val profileUserViewModel: ProfileUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_profil, container, false)

        val friendsButton : Button = root.findViewById<Button>(R.id.friendsButton)
        friendsButton.setOnClickListener{
            //Go to the friends fragment
            val userFriendsFragment = UserFriendsFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, userFriendsFragment,null)
                .addToBackStack(null)
                .commit()
        }

        val parametersButton : Button = root.findViewById<Button>(R.id.parametersButton)
        parametersButton.setOnClickListener{
            //Go to the parameters fragment
            val userSettingsFragment = UserSettingsFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, userSettingsFragment,null)
                .addToBackStack(null)
                .commit()
        }
        val achivementsButton : Button = root.findViewById<Button>(R.id.AchivementsButton)
        achivementsButton.setOnClickListener{
            //Go to the achivements fragment
            val userAchivementsFragment = UserAchivementsFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, userAchivementsFragment,null)
                .addToBackStack(null)
                .commit()
        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access and modify UI elements here
        val usernameText: TextView = view.findViewById(R.id.usernameText)
        //val button: Button = view.findViewById(R.id.button)

        usernameText.text = profileUserViewModel.username.value ?: "username inconnu"


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment user_profil.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileUserFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}