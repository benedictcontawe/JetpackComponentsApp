package com.example.jetpackcompose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

public class MainViewModel : ViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    private val repository : Repository
    private val list : MutableList<NasaHolderModel>

    constructor() {
        Log.d(TAG, "constructor")
        repository = Repository()
        list = mutableListOf<NasaHolderModel>()
    }

    init {
        Log.d(TAG, "initialize")
    }

    public fun observeAPOD() : SharedFlow<PagingData<NasaHolderModel>> {
        val request : NasaRequestModel = NasaRequestModel(Constants.API_KEY, 0)
        return repository.getFlowAPOD(request).map { pagingDatum : PagingData<NasaResponseModel> ->
            pagingDatum.map { response : NasaResponseModel ->
                list.add(NasaHolderModel(0, response))
                NasaHolderModel(list.size + 1, response)
            }
        }.cachedIn(viewModelScope).shareIn(viewModelScope, SharingStarted.Lazily)
    }
}