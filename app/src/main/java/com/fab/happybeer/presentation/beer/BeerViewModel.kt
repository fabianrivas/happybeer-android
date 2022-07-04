package com.fab.happybeer.presentation.beer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fab.happybeer.data.model.Beer
import com.fab.happybeer.repository.beer.BeerRepository

class BeerViewModel(
    private val beerRepository: BeerRepository,
) : ViewModel() {

    fun fetchBeerList(): LiveData<PagingData<Beer>> {
        return beerRepository.getBeerList().cachedIn(viewModelScope)
    }
}

class BeerViewModelFactory(
    private val repo: BeerRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BeerRepository::class.java)
            .newInstance(repo)
    }

}