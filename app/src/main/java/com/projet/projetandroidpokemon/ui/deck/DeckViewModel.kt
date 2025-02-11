package com.projet.projetandroidpokemon.ui.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projet.projetandroidpokemon.domain.CardsRepository
import com.projet.projetandroidpokemon.model.PokemonCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeckViewModel : ViewModel() {

    private val repository = CardsRepository

    private val _cards = MutableStateFlow<List<PokemonCard>>(emptyList())
    val cards: StateFlow<List<PokemonCard>> = _cards.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)

    private var selectedType: String? = null
    private var selectedRarity: String? = null
    private var selectedBonus: String? = null
    private var orderBy: String? = null



    fun loadCards(userEmail: String? = null, page: Int = 1, pageSize: Int = 20) {
        viewModelScope.launch {
            _isLoading.value = true
            if (userEmail != null) {
                repository.getCardsFromDatabase(userEmail, page, pageSize).collect { result ->
                    if (result.isSuccess) {
                        _cards.value = _cards.value + (result.getOrNull()?.map { it.toPokemonCard() } ?: emptyList())
                        _error.value = null
                    } else {
                        _error.value = result.exceptionOrNull()?.message
                    }
                    _isLoading.value = false
                }
            } else {
                repository.getFilteredAndOrderedCards(page, pageSize, selectedType, selectedRarity, selectedBonus, orderBy)
                    .collect { result ->
                        if (result.isSuccess) {
                            _cards.value = _cards.value + (result.getOrNull() ?: emptyList())
                            _error.value = null
                        } else {
                            _error.value = result.exceptionOrNull()?.message
                        }
                        _isLoading.value = false
                    }
            }
        }
    }

    fun resetCards() {
        _cards.value = emptyList()
        _error.value = null
    }
}
