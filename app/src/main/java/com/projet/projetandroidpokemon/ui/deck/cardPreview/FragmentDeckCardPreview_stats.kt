package com.projet.projetandroidpokemon.ui.deck.cardPreview

import android.annotation.SuppressLint
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

class FragmentDeckCardPreview_stats : Fragment() {
    private var pokemonCard: PokemonCard? = null



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            pokemonCard = it.getParcelable<PokemonCard>("PokemonCard") as PokemonCard

        }
        val root = inflater.inflate(R.layout.fragment_deck_card_preview_stats, container, false)

        val bundle = Bundle().apply {
            putParcelable("PokemonCard", pokemonCard)
        }

        // Create and set arguments for the Fragment
        val fragobjAttaques = FragmentDeckCardPreview_attaques().apply {
            arguments = bundle
        }

        val attaquesButton: Button = root.findViewById<Button>(R.id.attaquesButton)
        attaquesButton.setOnClickListener {
            // Go back to the previous fragment
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.pokemonPreview, fragobjAttaques)
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


        val hp: TextView = view.findViewById(R.id.cardInputPV)
        hp.text = pokemonCard?.hp ?: "Pokemon pv"

        val types: TextView = view.findViewById(R.id.cardInputType)
        types.text = pokemonCard?.types.toString() ?: "Pokemon types"

        val retreat: TextView = view.findViewById(R.id.cardInputRetreat)
        retreat.text = pokemonCard?.retreatCost.toString() ?: "Pokemon types"

        val weakness: TextView = view.findViewById(R.id.cardInputWeakness)
        val weaknessesText = pokemonCard?.weaknesses?.joinToString(", ") { weakness ->
            "${weakness.type}: ${weakness.value}"
        } ?: "Pokemon types"
        weakness.text = weaknessesText

    }
}