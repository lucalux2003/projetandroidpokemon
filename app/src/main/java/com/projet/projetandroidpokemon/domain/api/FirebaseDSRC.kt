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



    suspend fun addFriend(userEmail: String, friendEmail: String) {
        val formattedUserEmail = formatEmailKey(userEmail)
        val formattedFriendEmail = formatEmailKey(friendEmail)

        if (!areFriends(userEmail, friendEmail)) {
            val userFriendsRef = database.reference.child("friends").child(formattedUserEmail)
            val friendFriendsRef = database.reference.child("friends").child(formattedFriendEmail)

            userFriendsRef.child(formattedFriendEmail).setValue(true).await()

            friendFriendsRef.child(formattedUserEmail).setValue(true).await()
        }
    }


    suspend fun removeFriend(userEmail: String, friendEmail: String) {
        val formattedUserEmail = formatEmailKey(userEmail)
        val formattedFriendEmail = formatEmailKey(friendEmail)

        val userFriendsRef = database.reference.child("friends").child(formattedUserEmail)
        val friendFriendsRef = database.reference.child("friends").child(formattedFriendEmail)

        userFriendsRef.child(formattedFriendEmail).removeValue().await()
        friendFriendsRef.child(formattedUserEmail).removeValue().await()
    }

    suspend fun getFriends(userEmail: String): List<String> {
        val formattedUserEmail = formatEmailKey(userEmail)
        val snapshot = database.reference.child("friends").child(formattedUserEmail).get().await()
        return if (snapshot.exists()) {
            snapshot.children.mapNotNull { it.key }
        } else {
            emptyList()
        }
    }

    suspend fun areFriends(userEmail: String, friendEmail: String): Boolean {
        val formattedUserEmail = formatEmailKey(userEmail)
        val formattedFriendEmail = formatEmailKey(friendEmail)

        val snapshot = database.reference.child("friends").child(formattedUserEmail).child(formattedFriendEmail).get().await()
        return snapshot.exists()
    }
}
