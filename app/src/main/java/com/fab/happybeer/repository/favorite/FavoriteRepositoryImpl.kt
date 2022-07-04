package com.fab.happybeer.repository.favorite

import com.fab.happybeer.core.Result
import com.fab.happybeer.data.local.favorite.LocalFavoriteDataSource
import com.fab.happybeer.data.model.Favorite

class FavoriteRepositoryImpl(
    private val localFavoriteDataSource: LocalFavoriteDataSource
) : FavoriteRepository {

    override suspend fun saveFavorite(favorite: Favorite): Result<Long> =
        localFavoriteDataSource.saveFavorite(favorite)

    override suspend fun getFavoriteList(): Result<List<Favorite>> =
        localFavoriteDataSource.getFavoriteList()

    override suspend fun saveRating(favoriteId: Int, rating: Float): Result<Int> =
        localFavoriteDataSource.saveRating(favoriteId, rating)

}