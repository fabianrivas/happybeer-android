package com.fab.happybeer.repository.beer

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.fab.happybeer.core.Result
import com.fab.happybeer.data.model.Beer

interface BeerRepository {

    fun getBeerList(): LiveData<PagingData<Beer>>

}