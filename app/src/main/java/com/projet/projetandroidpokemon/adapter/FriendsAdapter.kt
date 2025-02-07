package com.projet.projetandroidpokemon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projet.projetandroidpokemon.R

class FriendsAdapter(private var friendsList: List<String>) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val friendName: TextView = itemView.findViewById(R.id.friendName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.friendName.text = friendsList[position]
    }

    override fun getItemCount(): Int = friendsList.size

    fun updateFriends(newFriends: List<String>) {
        friendsList = newFriends
        notifyDataSetChanged()
    }
}
