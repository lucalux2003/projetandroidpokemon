package com.projet.projetandroidpokemon.ui.draw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.projet.projetandroidpokemon.manager.InternetChecker
import com.projet.projetandroidpokemon.manager.UserSessionManager
import com.projet.projetandroidpokemon.databinding.FragmentDrawBinding
import com.projet.projetandroidpokemon.domain.api.FirebaseDSRC
import com.projet.projetandroidpokemon.domain.database.PokemonCardDataBase
import com.projet.projetandroidpokemon.model.PokemonCard
import com.projet.projetandroidpokemon.model.PokemonUserCards
import com.projet.projetandroidpokemon.ui.init.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DrawFragment : Fragment() {

    private var _binding: FragmentDrawBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DrawViewModel by viewModels()

    private var isGifPlayed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrawBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observeViewModel()

        binding.view2.setOnClickListener {
            viewModel.drawRandomCards()
        }

        return root
    }

    private fun observeViewModel() {
        viewModel.isAnimating.observe(viewLifecycleOwner) { isAnimating ->
            val mainActivity = requireActivity() as? MainActivity
            mainActivity?.setNavigationEnabled(false)

            binding.cardImage.visibility = if (!isAnimating) View.VISIBLE else View.GONE
            binding.view2.isEnabled = !isAnimating

            if (isAnimating) {
                if (!isGifPlayed) {
                    binding.gifAnimation.visibility = View.VISIBLE
                    binding.cardShow.visibility = View.GONE
                    isGifPlayed = true
                }

                binding.gifAnimation.postDelayed({
                    binding.gifAnimation.visibility = View.GONE
                    binding.progressBar2.visibility = View.VISIBLE
                }, 2000)
            } else {
                binding.progressBar2.visibility = View.GONE
            }
        }

        viewModel.cards.observe(viewLifecycleOwner) { cards ->
            showCardsSequentially(cards)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
        }
    }

    private fun showCardsSequentially(cards: List<PokemonCard>) {
        val mainActivity = requireActivity() as? MainActivity
        viewLifecycleOwner.lifecycleScope.launch {

            binding.cardShow.visibility = View.VISIBLE
            binding.cardImage.visibility = View.GONE
            binding.gifAnimation.visibility = View.GONE

            cards.forEach { card ->
                saveCardToDatabase(card)
            }

            cards.forEach { card ->
                displayCard(card)
                delay(2000)
            }

            mainActivity?.setNavigationEnabled(true)

            binding.cardImage.visibility = View.VISIBLE
            binding.cardShow.visibility = View.GONE
            binding.view2.isEnabled = true
        }
    }


    private fun displayCard(card: PokemonCard) {
        Glide.with(requireContext()).load(card.images?.large).into(binding.cardShow)
    }

    private fun saveCardToDatabase(card: PokemonCard) {
        val userEmail = UserSessionManager(requireContext()).getUserEmail()
        val internetChecker = InternetChecker(requireContext())

        if (userEmail != null) {
            val pokemonCard = card.id?.let {
                PokemonUserCards(
                    userEmail = userEmail,
                    pokemonName = card.name,
                    cardId = it,
                    types = card.types,
                    rarity = card.rarity,
                    supertype = card.supertype,
                    series = card.series?.toSet(),
                    images = card.images
                )
            }

            lifecycleScope.launch {
                if (pokemonCard != null) {
                    PokemonCardDataBase.getInstance().PokemonDAO().insertPokemon(pokemonCard)

                    if (internetChecker.isInternetAvailable()) {
                        try {
                            card.id?.let { cardId ->
                                FirebaseDSRC.saveCardToFirebase(userEmail, cardId)
                            }
                        } catch (e: Exception) {
                            Toast.makeText(requireContext(), "Erreur lors de la sauvegarde sur Firebase.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Pas de connexion Internet pour sauvegarder sur Firebase.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Erreur lors de la sauvegarde de la carte.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Utilisateur non connecté", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
