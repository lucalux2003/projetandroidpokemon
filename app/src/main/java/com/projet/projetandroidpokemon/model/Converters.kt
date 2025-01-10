package com.projet.projetandroidpokemon.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromSet(value: Set<String>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSet(value: String?): Set<String>? {
        val setType = object : TypeToken<Set<String>>() {}.type
        return gson.fromJson(value, setType)
    }

    @TypeConverter
    fun fromCardImages(cardImages: CardImages?): String? {
        return gson.toJson(cardImages)
    }

    @TypeConverter
    fun toCardImages(value: String?): CardImages? {
        return gson.fromJson(value, CardImages::class.java)
    }

    @TypeConverter
    fun fromPokemonCard(value: PokemonCard?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPokemonCard(value: String?): PokemonCard? {
        return gson.fromJson(value, PokemonCard::class.java)
    }
}
