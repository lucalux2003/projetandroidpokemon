package com.projet.projetandroidpokemon

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.projet.projetandroidpokemon.domain.database.PokemonCardDataBase
import com.projet.projetandroidpokemon.domain.database.dao.UserDAO
import com.projet.projetandroidpokemon.model.User

class SyncManager(private val context: Context) {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://projetandroidpokemon-default-rtdb.europe-west1.firebasedatabase.app")
    private val userDAO: UserDAO = PokemonCardDataBase.getInstance().userDAO()

    fun syncIfConnected() {
        if (isInternetAvailable()) {
            syncRoomToFirebase()
        }
    }

    private fun syncRoomToFirebase() {
        val usersLiveData: LiveData<List<User>> = userDAO.getAllLiveData()

        usersLiveData.observeForever { users ->
            users?.forEach { user ->
                val userId = user.email.replace(".", "_")

                database.reference.child("users").orderByChild("email").equalTo(user.email).get()
                    .addOnSuccessListener { dataSnapshot ->
                        if (!dataSnapshot.exists()) {
                            database.reference.child("users").child(userId).setValue(user)
                                .addOnSuccessListener {
                                    Log.d("SyncManager", "Data for user $userId synced to Firebase")
                                }
                                .addOnFailureListener {
                                    Log.e("SyncManager", "Data sync for user $userId failed", it)
                                }
                        } else {
                            Log.d("SyncManager", "User with email ${user.email} already exists in Firebase. Not syncing.")
                        }
                    }
                    .addOnFailureListener {
                        Log.e("SyncManager", "Failed to check user email existence", it)
                    }
            }
        }
    }


    private fun syncRoomToFirebaseUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val usersLiveData: LiveData<List<User>> = userDAO.getAllLiveData()

            usersLiveData.observeForever { users ->
                users?.forEach { user ->
                    val userId = currentUser.uid
                    Log.d("SyncManager", "User ID: $userId")

                    if (userId != null) {
                        database.reference.child("users").child(userId).setValue(user)
                            .addOnSuccessListener {
                                Log.d("SyncManager", "Data synced to Firebase")
                            }
                            .addOnFailureListener {
                                Log.e("SyncManager", "Data sync failed", it)
                            }
                    }
                }
            }
        } else {
            Log.e("SyncManager", "User is not logged in. Cannot sync data.")
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
