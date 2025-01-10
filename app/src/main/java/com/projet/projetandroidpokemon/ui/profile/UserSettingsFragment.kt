package com.projet.projetandroidpokemon.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.projet.projetandroidpokemon.R


/**
 * A simple [Fragment] subclass.
 * Use the [user_settings.newInstance] factory method to
 * create an instance of this fragment.
 */
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
            // Go back to the previous fragment
            parentFragmentManager.popBackStack()
        }
        // Inflate the layout for this fragment
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment user_settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserSettingsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}