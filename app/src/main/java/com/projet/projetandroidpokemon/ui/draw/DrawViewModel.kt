package com.projet.projetandroidpokemon.ui.draw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projet.projetandroidpokemon.domain.CardsRepository
import com.projet.projetandroidpokemon.model.PokemonCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DrawViewModel : ViewModel() {

    private val _isAnimating = MutableLiveData<Boolean>()
    val isAnimating: LiveData<Boolean> get() = _isAnimating

    private val _cards = MutableLiveData<List<PokemonCard>>()
    val cards: LiveData<List<PokemonCard>> get() = _cards

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var isProcessing = false

    fun drawRandomCards() {
        if (isProcessing) return

        isProcessing = true
        _isAnimating.value = true

        viewModelScope.launch {
            CardsRepository.drawRandomCards(5).collect { result ->
                result.onSuccess { cardList ->
                    _cards.value = cardList
                    _isAnimating.value = false
                    isProcessing = false
                }
                result.onFailure {
                    _error.value = "Erreur : ${it.message}"
                    _isAnimating.value = false
                    isProcessing = false
                }
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
