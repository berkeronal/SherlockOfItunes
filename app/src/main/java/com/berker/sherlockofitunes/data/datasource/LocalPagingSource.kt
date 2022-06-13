package com.berker.sherlockofitunes.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState

class LocalPagingSource<T : Any>(
    private val block: suspend (Int) -> List<T>?
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val position = params.key ?: STARTING_INDEX
        val offset = (position - 1) * 20

        return try {
            block(offset)?.let {
                LoadResult.Page(
                    data = it,
                    prevKey = if (position == STARTING_INDEX) null else position.minus(1),
                    nextKey = if (it.isEmpty()) null else position.plus(1)
                )
            } ?: run {
                LoadResult.Error(Exception("Error"))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_INDEX = 1
    }
}