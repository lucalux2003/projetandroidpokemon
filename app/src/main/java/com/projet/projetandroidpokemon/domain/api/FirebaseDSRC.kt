package com.projet.projetandroidpokemon.domain.api

import com.google.firebase.database.FirebaseDatabase
import com.projet.projetandroidpokemon.model.User
import kotlinx.coroutines.tasks.await

object FirebaseDSRC {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://projetandroidpokemon-default-rtdb.europe-west1.firebasedatabase.app")

    private fun formatEmailKey(email: String): String {
        return email.replace(".", "_")
    }

    suspend fun isEmailInFirebase(email: String): Boolean {
        val formattedEmail = formatEmailKey(email)
        val snapshot = database.reference.child("users").child(formattedEmail).get().await()
        return snapshot.exists()
    }

    suspend fun addUserToFirebase(user: User) {
        val formattedEmail = formatEmailKey(user.email)
        database.reference.child("users").child(formattedEmail).setValue(user).await()
    }

    suspend fun getUserByEmail(email: String): User? {
        val snapshot = database.reference.child("users").orderByChild("email").equalTo(email).get().await()
        return if (snapshot.exists()) {
            snapshot.children.first().getValue(User::class.java)
        } else {
            null
        }
    }

    suspend fun authenticateUser(email: String, hashedPassword: String): Boolean {
        val user = getUserByEmail(email)
        return user?.password == hashedPassword
    }

    suspend fun getAllUsers(): List<User> {
        val snapshot = database.reference.child("users").get().await()
        return if (snapshot.exists()) {
            snapshot.children.mapNotNull { it.getValue(User::class.java) }
        } else {
            emptyList()
        }
    }

    suspend fun saveCardToFirebase(email: String, cardId: String) {
        val formattedEmail = formatEmailKey(email)
        database.reference
            .child("userCards")
            .child(formattedEmail)
            .child(cardId)
            .setValue(true)
            .await()
    }

    suspend fun addFriendToFirebase(scannedEmail: String, email: String) {
        if (email != null) {
            val formattedEmail = formatEmailKey(email)
            val friendFormattedEmail = formatEmailKey(scannedEmail)

            val userFriendsRef = FirebaseDatabase.getInstance().getReference("userFriends").child(formattedEmail)
            userFriendsRef.child(friendFormattedEmail).setValue(true).await()

            val friendRef = FirebaseDatabase.getInstance().getReference("userFriends").child(friendFormattedEmail)
            friendRef.child(formattedEmail).setValue(true).await()
        }
    }
}
