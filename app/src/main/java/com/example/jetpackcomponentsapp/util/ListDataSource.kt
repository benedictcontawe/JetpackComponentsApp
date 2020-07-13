package com.example.jetpackcomponentsapp.util

import androidx.paging.PositionalDataSource

class ListDataSource<T : Any>(private val items : List<T>) : PositionalDataSource<T>() {

    override fun loadInitial(params : LoadInitialParams, callback : LoadInitialCallback<T>) {
        callback.onResult(items, 0, items.size)
    }

    override fun loadRange(params : LoadRangeParams, callback : LoadRangeCallback<T>) {
        val start = params.startPosition
        val end = params.startPosition + params.loadSize
        callback.onResult(items.subList(start, end))
    }
}
/*
class ListDataSource<T>(private val items : List<T>) : PositionalDataSource<T>() {

    override fun loadInitial(params : LoadInitialParams, callback : LoadInitialCallback<T>) {
        callback.onResult(items, 0, items.size)
    }

    override fun loadRange(params : LoadRangeParams, callback : LoadRangeCallback<T>) {
        val start = params.startPosition
        val end = params.startPosition + params.loadSize
        callback.onResult(items.subList(start, end))
    }
}
*/