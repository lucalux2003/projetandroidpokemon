package com.projet.projetandroidpokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projet.projetandroidpokemon.domain.api.FirebaseDSRC
import kotlinx.coroutines.launch

class UserFriendsViewModel : ViewModel() {

    private val _friendsList = MutableLiveData<List<String>>()
    val friendsList: LiveData<List<String>> get() = _friendsList

    private val _addFriendResult = MutableLiveData<Boolean>()
    val addFriendResult: LiveData<Boolean> get() = _addFriendResult

    fun loadFriends(userEmail: String) {
        viewModelScope.launch {
            val friends = FirebaseDSRC.getFriends(userEmail)
            _friendsList.postValue(friends)
        }
    }

    fun addFriend(userEmail: String, friendEmail: String) {
        viewModelScope.launch {
            FirebaseDSRC.addFriend(userEmail, friendEmail)
            _addFriendResult.postValue(true)
            loadFriends(userEmail)
        }
    }
}
