package com.fab.happybeer.repository.favorite

import com.fab.happybeer.core.Result
import com.fab.happybeer.data.model.Favorite

interface FavoriteRepository {

    suspend fun saveFavorite(favorite: Favorite): Result<Long>

    suspend fun getFavoriteList(): Result<List<Favorite>>

    suspend fun saveRating(favoriteId: Int, rating: Float): Result<Int>
}