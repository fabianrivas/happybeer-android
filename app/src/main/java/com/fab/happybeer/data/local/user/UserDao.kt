package com.fab.happybeer.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fab.happybeer.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User): Long

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUser(username: String): User
}