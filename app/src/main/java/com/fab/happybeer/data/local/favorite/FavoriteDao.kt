package com.fab.happybeer.data.local.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fab.happybeer.data.model.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    suspend fun getFavoriteList(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(movie: Favorite): Long

    @Query("UPDATE favorite SET rating = :rating WHERE id = :favoriteId")
    suspend fun saveRating(favoriteId: Int, rating: Float): Int

}