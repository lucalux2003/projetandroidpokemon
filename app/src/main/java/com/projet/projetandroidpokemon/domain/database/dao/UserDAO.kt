package com.projet.projetandroidpokemon.domain.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.projet.projetandroidpokemon.model.User

@Dao
interface UserDAO {

	@Insert
	suspend fun insertUser(user : User)

	@Update
	suspend fun updateUser(user : User)

	@Delete
	suspend fun deleteUser(user: User)

	@Query("Select * from User")
	suspend fun getAll(): List<User>


	@Query("SELECT * FROM User")
	fun getAllLiveData(): LiveData<List<User>>


	@Query("Select * from User where email = :email limit 1")
	suspend fun getUserFromMail(email: String): User

	@Query("Select * from User where name = :name limit 1")
	suspend fun getUserFromName(name: String): User

}