package com.projet.projetandroidpokemon.ui.deck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projet.projetandroidpokemon.CardAdapter
import com.projet.projetandroidpokemon.UserSessionManager
import com.projet.projetandroidpokemon.databinding.FragmentDeckBinding
import kotlinx.coroutines.launch

class DeckFragment : Fragment() {

    private lateinit var viewModel: DeckViewModel
    private lateinit var adapter: CardAdapter
    private var currentPage = 1
    private var isFromApi = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDeckBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        adapter = CardAdapter(emptyList())

        binding.recyclerViewCards.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewCards.adapter = adapter

        viewModel.resetCards()
        viewModel.loadCards(page = currentPage)
        binding.deckTxt.text = "Pokedex"


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cards.collect { cards ->
                adapter.updateData(cards)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        setupScrollListener(binding)

        binding.ownerDeck.setOnClickListener {
            val userEmail = UserSessionManager(requireContext()).getUserEmail()
            isFromApi = false
            currentPage = 1
            binding.deckTxt.text = "Votre deck"
            viewModel.resetCards()
            viewModel.loadCards(userEmail = userEmail, page = currentPage)
        }

        binding.onlineDeck.setOnClickListener {
            isFromApi = true
            currentPage = 1
            viewModel.resetCards()
            binding.deckTxt.text = "Pokedex"
            viewModel.loadCards(page = currentPage)
        }

        return root
    }

    private fun setupScrollListener(binding: FragmentDeckBinding) {
        binding.recyclerViewCards.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!viewModel.isLoading.value) {
                        currentPage++
                        if (isFromApi) {
                            viewModel.loadCards(page = currentPage)
                        } else {
                            val userEmail = UserSessionManager(requireContext()).getUserEmail()
                            viewModel.loadCards(userEmail = userEmail, page = currentPage)
                        }
                    }
                }
            }
        })
    }
}
