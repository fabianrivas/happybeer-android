package com.fab.happybeer.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fab.happybeer.core.AppConstants
import com.fab.happybeer.data.model.Beer

class BeerPagingSource(
    private val webService: WebService
) : PagingSource<Int, Beer>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        return try {
            val page = params.key ?: 1
            val response = webService.getBeerList(page, AppConstants.PAGE_SIZE)
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}