package com.fab.happybeer.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.fab.happybeer.core.Result
import com.fab.happybeer.data.model.Beer
import com.fab.happybeer.data.model.toFavorite
import com.fab.happybeer.repository.favorite.FavoriteRepository
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    fun saveFavorite(beer: Beer) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            kotlin.runCatching {
                favoriteRepository.saveFavorite(beer.toFavorite())
            }.onSuccess { result ->
                emit(result)
            }.onFailure { throwable ->
                emit(Result.Failure(Exception(throwable.message)))
            }
        }

    fun fetchFavoriteList() =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            emit(Result.Loading())
            kotlin.runCatching {
                favoriteRepository.getFavoriteList()
            }.onSuccess { favoriteList ->
                emit(favoriteList)
            }.onFailure { throwable ->
                emit(Result.Failure(Exception(throwable.message)))
            }
        }

    fun saveRating(favoriteId: Int, rating: Float) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            kotlin.runCatching {
                favoriteRepository.saveRating(favoriteId, rating)
            }.onSuccess { result ->
                emit(result)
            }.onFailure { throwable ->
                emit(Result.Failure(Exception(throwable.message)))
            }
        }
}

class FavoriteViewModelFactory(
    private val repo: FavoriteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(FavoriteRepository::class.java).newInstance(repo)
    }

}
