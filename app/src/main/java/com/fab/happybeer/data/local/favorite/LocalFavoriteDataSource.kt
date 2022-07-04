package com.fab.happybeer.data.local.favorite

import com.fab.happybeer.core.Result
import com.fab.happybeer.data.model.Favorite

class LocalFavoriteDataSource(private val favoriteDao: FavoriteDao) {

    suspend fun saveFavorite(favorite: Favorite): Result<Long> =
        Result.Success(favoriteDao.saveFavorite(favorite))

    suspend fun getFavoriteList(): Result<List<Favorite>> =
        Result.Success(favoriteDao.getFavoriteList())

    suspend fun saveRating(favoriteId: Int, rating: Float): Result<Int> =
        Result.Success(favoriteDao.saveRating(favoriteId, rating))

}