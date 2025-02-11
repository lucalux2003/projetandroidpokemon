package com.projet.projetandroidpokemon.ui.deck.cardPreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.projet.projetandroidpokemon.R
import com.projet.projetandroidpokemon.model.PokemonCard

class FragmentDeckCardPreview_attaques : Fragment() {
    private var pokemonCard: PokemonCard? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            pokemonCard = it.getParcelable<PokemonCard>("PokemonCard") as PokemonCard

        }
        val root = inflater.inflate(R.layout.fragment_deck_card_preview_attaques, container, false)

        val bundle = Bundle().apply {
            putParcelable("PokemonCard", pokemonCard)
        }

        val fragobjStats = FragmentDeckCardPreview_stats().apply {
            arguments = bundle
        }

        val statsButton: Button = root.findViewById<Button>(R.id.statsButton)
        statsButton.setOnClickListener {
            // Go back to the previous fragment
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.pokemonPreview, fragobjStats)
                .addToBackStack(null)
                .commit()
        }

        val fragobjDivers = FragmentDeckCardPreview_divers().apply {
            arguments = bundle
        }

        val diversButton: Button = root.findViewById<Button>(R.id.diversButton)
        diversButton.setOnClickListener {
            // Go back to the previous fragment
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.pokemonPreview, fragobjDivers)
                .addToBackStack(null)
                .commit()
        }

        val closeButton: ImageButton = root.findViewById<ImageButton>(R.id.closeImageButton)
        closeButton.setOnClickListener {
            // Go back to the previous fragment
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
        // Inflate the layout for this fragment
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        val pokemonName: TextView = view.findViewById(R.id.PokemonNameText)
        pokemonName.text = pokemonCard?.name ?: "Pokemon name"

        val imageUrl = pokemonCard?.images?.small
        val cardDisplayButton: ImageButton = view.findViewById(R.id.cardDisplayButton)

        Glide.with(requireContext())
            .load(imageUrl)
            .into(cardDisplayButton)

    }
}