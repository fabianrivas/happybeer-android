package com.fab.happybeer.repository.beer

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.fab.happybeer.data.model.Beer
import com.fab.happybeer.data.remote.RemoteBeerDataSource

class BeerRepositoryImpl(
    private val remoteBeerDataSource: RemoteBeerDataSource
) : BeerRepository {

    override fun getBeerList(): LiveData<PagingData<Beer>> = remoteBeerDataSource.getBeerList()
}