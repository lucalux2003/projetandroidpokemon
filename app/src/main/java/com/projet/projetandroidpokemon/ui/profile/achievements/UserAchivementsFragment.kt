package com.projet.projetandroidpokemon.ui.profile.achievements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.projet.projetandroidpokemon.R


/**
 * A simple [Fragment] subclass.
 * Use the [UserAchivementsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserAchivementsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_achivement, container, false)

        val returnButton: ImageButton = root.findViewById<ImageButton>(R.id.backButton)
        returnButton.setOnClickListener {
            // Go back to the previous fragment
            parentFragmentManager.popBackStack()
        }


        return root
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserAchivementsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}