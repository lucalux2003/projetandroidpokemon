package com.projet.projetandroidpokemon.ui.profile.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.projet.projetandroidpokemon.R
import com.projet.projetandroidpokemon.manager.UserSessionManager

class QrCodeFragment : Fragment() {

    private val qrCodeViewModel: QrCodeViewModel by viewModels()
    private lateinit var currentUserEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = UserSessionManager(requireContext())
        currentUserEmail = sessionManager.getUserEmail()
            ?: throw IllegalStateException("Aucun e-mail enregistré dans la session")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_qr_code, container, false)

        val returnButton: ImageButton = root.findViewById(R.id.backButton)
        val qrCodeImageView: ImageView = root.findViewById(R.id.imageView7)

        returnButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        qrCodeViewModel.qrBitmap.observe(viewLifecycleOwner) { bitmap ->
            bitmap?.let { qrCodeImageView.setImageBitmap(it) }
        }

        qrCodeViewModel.generateQRCode(currentUserEmail)

        return root
    }


}
