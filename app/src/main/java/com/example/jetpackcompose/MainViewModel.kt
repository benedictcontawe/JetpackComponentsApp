package com.example.jetpackcompose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class MainViewModel : ViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    private val repository : Repository
    private val pagingAPOD : Flow<PagingData<NasaHolderModel>>

    constructor() {
        Log.d(TAG, "constructor")
        repository = Repository()
        // Created once: calling getFlowAPOD() again (e.g. on every recomposition) would start a new Pager
        // and reload from page 1. cachedIn keeps the loaded pages across recomposition and rotation.
        val request : NasaRequestModel = NasaRequestModel(Constants.API_KEY, Constants.APPEND_COUNT)
        pagingAPOD = repository.getFlowAPOD(request).map { pagingData : PagingData<NasaResponseModel> ->
            pagingData.map { response : NasaResponseModel -> NasaHolderModel(response) }
        }.cachedIn(viewModelScope)
    }

    init {
        Log.d(TAG, "initialize")
    }

    public fun observeAPOD() : Flow<PagingData<NasaHolderModel>> {
        return pagingAPOD
    }
}
