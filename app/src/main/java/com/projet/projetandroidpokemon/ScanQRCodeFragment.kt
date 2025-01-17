package com.projet.projetandroidpokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.projet.projetandroidpokemon.databinding.FragmentScanQrCodeBinding
import com.projet.projetandroidpokemon.domain.api.FirebaseDSRC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanQRCodeFragment : Fragment() {

    private var _binding: FragmentScanQrCodeBinding? = null
    private val binding get() = _binding!!
    private lateinit var scannerView: ZXingScannerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanQrCodeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        scannerView = binding.zxscan

        scannerView.setResultHandler { result ->
            val scannedEmail = result.text
            if (scannedEmail.isNotEmpty()) {
                val currentUserEmail = UserSessionManager(requireContext()).getUserEmail()
                if (currentUserEmail != null) {
                    addFriend(currentUserEmail, scannedEmail)
                } else {
                    Toast.makeText(requireContext(), "Utilisateur non connecté.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "QR Code invalide.", Toast.LENGTH_SHORT).show()
            }
        }

        scannerView.startCamera()
        return root
    }

    private fun addFriend(currentUserEmail: String, scannedEmail: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                FirebaseDSRC.addFriendToFirebase(scannedEmail, currentUserEmail)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Ami ajouté avec succès!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Erreur: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
