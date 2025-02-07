package com.projet.projetandroidpokemon.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projet.projetandroidpokemon.QrCodeFragment
import com.projet.projetandroidpokemon.ui.activity.QrScannerActivity
import com.projet.projetandroidpokemon.R
import com.projet.projetandroidpokemon.UserSessionManager
import com.projet.projetandroidpokemon.viewmodel.UserFriendsViewModel

class UserFriendsFragment : Fragment() {

    private val userFriendsViewModel: UserFriendsViewModel by viewModels()
    private lateinit var currentUserEmail: String

    private val QR_SCAN_REQUEST_CODE = 1001

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
        val root = inflater.inflate(R.layout.fragment_user_friends, container, false)

        val returnButton: ImageButton = root.findViewById(R.id.backButton)
        returnButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val qrCodeButton: ImageButton = root.findViewById(R.id.QRCodeButton)
        qrCodeButton.setOnClickListener {
            val qrCodeFragment = QrCodeFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, qrCodeFragment, null)
                .addToBackStack(null)
                .commit()
        }

        val scanButton: ImageButton = root.findViewById(R.id.cameraButton)
        scanButton.setOnClickListener {
            val intent = Intent(requireContext(), QrScannerActivity::class.java)
            startActivityForResult(intent, QR_SCAN_REQUEST_CODE)
        }

        val friendsRecyclerView: RecyclerView = root.findViewById(R.id.friendsRecyclerView)
        friendsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val friendsAdapter = FriendsAdapter(emptyList())
        friendsRecyclerView.adapter = friendsAdapter

        userFriendsViewModel.friendsList.observe(viewLifecycleOwner) { friends ->
            friendsAdapter.updateFriends(friends)
        }

        userFriendsViewModel.addFriendResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Ami ajouté avec succès !", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        userFriendsViewModel.loadFriends(currentUserEmail)

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == QR_SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val scannedEmail = data?.getStringExtra("scanned_email")
            Log.d("scannedEmail",scannedEmail+"")
            scannedEmail?.let {
                userFriendsViewModel.addFriend(currentUserEmail, it)
            }
        }
    }
}
