package com.projet.projetandroidpokemon.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.projet.projetandroidpokemon.QrCodeFragment
import com.projet.projetandroidpokemon.R


/**
 * A simple [Fragment] subclass.
 * Use the [UserFriendsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFriendsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_friends, container, false)

        val returnButton: ImageButton = root.findViewById<ImageButton>(R.id.backButton)
        returnButton.setOnClickListener {
            // Go back to the previous fragment
            parentFragmentManager.popBackStack()
        }

        val qrCodeButton: ImageButton = root.findViewById<ImageButton>(R.id.QRCodeButton)
        qrCodeButton.setOnClickListener {
            val qrCodeFragment = QrCodeFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, qrCodeFragment,null)
                .addToBackStack(null)
                .commit()
        }


        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFriends.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFriendsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}