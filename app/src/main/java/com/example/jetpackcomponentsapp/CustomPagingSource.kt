package com.example.jetpackcomponentsapp

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetpackcomponentsapp.room.CustomDAO
import com.example.jetpackcomponentsapp.room.CustomEntity
import com.example.jetpackcomponentsapp.util.Constants

public class CustomPagingSource : PagingSource<Int, CustomEntity> {

    companion object {
        private val TAG : String = CustomPagingSource::class.java.getSimpleName()
    }

    private val customDao : CustomDAO?

    constructor(customDao : CustomDAO) {
        Log.d(TAG, "constructor")
        this.customDao = customDao
    }

    override suspend fun load(params : LoadParams<Int>) : LoadResult<Int, CustomEntity> {
        return try {
            val page = params.key ?: Constants.PAGING_SOURCE_PAGE_INDEX

            val entities = customDao?.getAll(params.loadSize, page * params.loadSize)

            val prevKey = if (page == 0) null
            else page - 1

            val nextKey = if (entities?.isEmpty() == true) null
            else page + 1

            Log.d(TAG, "load $page $prevKey $nextKey ${entities}")
            LoadResult.Page(
                data = entities!!,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception : Exception) {
            Log.e(TAG, "Error ${exception.message}", exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state : PagingState<Int, CustomEntity>) : Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}