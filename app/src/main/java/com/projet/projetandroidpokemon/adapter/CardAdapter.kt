package com.projet.projetandroidpokemon.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projet.projetandroidpokemon.R
import com.projet.projetandroidpokemon.model.PokemonCard
import com.projet.projetandroidpokemon.ui.deck.cardPreview.FragmentDeckCardPreview_stats

class CardAdapter(private var cardList: List<PokemonCard>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardImage: ImageView = view.findViewById(R.id.image_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]

        val imageUrl = card.images?.large
        if (imageUrl != null && imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .into(holder.cardImage)
        }

        holder.itemView.setOnClickListener {
            // Create a Bundle
            val bundle = Bundle().apply {
                putParcelable("PokemonCard", card)
            }

            val fragobj = FragmentDeckCardPreview_stats().apply {
                arguments = bundle
            }

            val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.pokemonPreview, fragobj)
                .addToBackStack(null)
                .commit()
        }

    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun updateData(newCards: List<PokemonCard>) {
        cardList = newCards
        notifyDataSetChanged()
    }
}
