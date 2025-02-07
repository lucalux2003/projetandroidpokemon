package com.projet.projetandroidpokemon.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class PokemonCard(
	val name: String,
	val id: String? = null,
	var types: List<String>? = null,
	var rarity: String? = null,
	var supertype: String? = null,
	var series: List<String>? = null,
	var images: CardImages? = null,
	var hp: String? = null,
	var retreatCost: List<String>? = null,
	var weaknesses: List<CardWeaknesses>? = null,
	//var artist: String?,


) : Parcelable {
	override fun describeContents(): Int = 0

	override fun writeToParcel(dest: Parcel, flags: Int) {
		dest.writeString(name)
		dest.writeString(id)
		dest.writeStringList(types)
		dest.writeString(rarity)
		dest.writeString(supertype)
		dest.writeStringList(series)
		dest.writeParcelable(images, flags)
		dest.writeString(hp)
		dest.writeStringList(retreatCost)
		dest.writeTypedList(weaknesses)
		//dest.writeString(artist)

	}

	companion object CREATOR : Parcelable.Creator<PokemonCard> {
		override fun createFromParcel(parcel: Parcel): PokemonCard {
			val name = parcel.readString() ?: ""
			val id = parcel.readString()
			val types = parcel.createStringArrayList()
			val rarity = parcel.readString()
			val supertype = parcel.readString()
			val series = parcel.createStringArrayList()
			val images: CardImages? = parcel.readParcelable(CardImages::class.java.classLoader)
			val hp = parcel.readString()
			val retreatCost = parcel.createStringArrayList()
			val weaknesses: List<CardWeaknesses>? = parcel.createTypedArrayList(CardWeaknesses.CREATOR)
			val artist = parcel.readString()

			return PokemonCard(
				name, id, types,
				rarity, supertype, series,
				images, hp, retreatCost, weaknesses,
				/*artist*/)
		}

		override fun newArray(size: Int): Array<PokemonCard?> = arrayOfNulls(size)
	}
}

data class CardImages(
	val small: String,
	val large: String
): Parcelable {
	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(small)
		parcel.writeString(large)
	}

	override fun describeContents(): Int = 0

	companion object CREATOR : Parcelable.Creator<CardImages> {
		override fun createFromParcel(parcel: Parcel): CardImages {
			val small = parcel.readString() ?: ""
			val large = parcel.readString() ?: ""
			return CardImages(small, large)
		}

		override fun newArray(size: Int): Array<CardImages?> = arrayOfNulls(size)
	}
}

data class CardWeaknesses(
	val type: String,
	val value: String
):Parcelable{
	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(type)
		parcel.writeString(value)
	}

	override fun describeContents(): Int = 0

	companion object CREATOR : Parcelable.Creator<CardWeaknesses> {
		override fun createFromParcel(parcel: Parcel): CardWeaknesses {
			val type = parcel.readString() ?: ""
			val value = parcel.readString() ?: ""
			return CardWeaknesses(type, value)
		}

		override fun newArray(size: Int): Array<CardWeaknesses?> = arrayOfNulls(size)
	}
}

data class CardSet(
	val id: String,
	val name: String,
	val series: String
):Parcelable{
	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(id)
		parcel.writeString(name)
		parcel.writeString(series)

	}

	override fun describeContents(): Int = 0

	companion object CREATOR : Parcelable.Creator<CardSet> {
		override fun createFromParcel(parcel: Parcel): CardSet {
			val type = parcel.readString() ?: ""
			val value = parcel.readString() ?: ""
			val series = parcel.readString() ?: ""
			return CardSet(type, value,series)
		}

		override fun newArray(size: Int): Array<CardSet?> = arrayOfNulls(size)
	}
}