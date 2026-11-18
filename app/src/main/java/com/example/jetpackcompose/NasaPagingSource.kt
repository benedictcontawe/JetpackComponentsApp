package com.example.jetpackcompose

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response

public class NasaPagingSource : PagingSource<Int, NasaResponseModel> {

    companion object {
        private val TAG : String = NasaPagingSource::class.java.getSimpleName()
    }

    private val nasaAPI : NasaAPI
    private val request : NasaRequestModel

    constructor(nasaAPI : NasaAPI, request : NasaRequestModel) {
        this.nasaAPI = nasaAPI
        this.request = request
        Log.d(TAG, "constructor ${request.key} ${request.count}")
    }

    override suspend fun load(params : LoadParams<Int>) : LoadResult<Int, NasaResponseModel> {
        return try {
            val position = params.key ?: Constants.PAGING_SOURCE_PAGE_INDEX

            // count= returns that many random days, so ask for exactly one page's worth each time
            // (params.loadSize comes from PagingConfig: initialLoadSize for the first page, pageSize after).
            val response : Response<List<NasaResponseModel>> = nasaAPI.getAstronomyPictureOfTheDay(request.key!!, params.loadSize)
            val body : List<NasaResponseModel>? = response.body()
            if (response.isSuccessful().not() || body == null) {
                // e.g. 429 too many requests, 500 server error: show the error state instead of crashing
                return LoadResult.Error(HttpException(response))
            }

            val prevKey = if (position == Constants.PAGING_SOURCE_PAGE_INDEX) null
            else position - 1

            val nextKey = if (body.isEmpty()) null
            else position + 1
            Log.d(TAG, "load $position $prevKey $nextKey size ${body.size}")
            LoadResult.Page (
                data = body,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception : Exception) {
            Log.e(TAG, "Error ${exception.message}", exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state : PagingState<Int, NasaResponseModel>) : Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }
}