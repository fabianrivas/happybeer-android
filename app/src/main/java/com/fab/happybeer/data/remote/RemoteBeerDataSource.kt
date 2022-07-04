package com.fab.happybeer.data.remote

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.fab.happybeer.core.AppConstants.PAGE_SIZE
import com.fab.happybeer.data.model.Beer

class RemoteBeerDataSource(private val webService: WebService) {

    fun getBeerList(): LiveData<PagingData<Beer>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                BeerPagingSource(webService)
            }, initialKey = 1
        ).liveData
    }

}