package com.example.jetpackcomponentsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.*

public class MainViewModel : ViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    private val repository : Repository
    private val list : MutableList<NasaHolderModel>

    constructor() {
        repository = Repository()
        list = mutableListOf<NasaHolderModel>()
    }

    public suspend fun observeAPOD() : SharedFlow<PagingData<NasaHolderModel>> {
        val request : NasaRequestModel = NasaRequestModel(Constants.API_KEY, 0)
        return repository.getFlowAPOD(request).map { pagingDatum ->
            pagingDatum.map { response ->
                list.add(NasaHolderModel(0, response))
                NasaHolderModel(list.size + 1, response)
            }
        }.shareIn(viewModelScope, SharingStarted.Lazily)
    }

    public fun observeLiveAPOD() : LiveData<PagingData<NasaHolderModel>> {
        val request : NasaRequestModel = NasaRequestModel(Constants.API_KEY, 0)
        return repository.getLiveAPOD(request).map { pagingDatum ->
            pagingDatum.map { response ->
                list.add(NasaHolderModel(0, response))
                NasaHolderModel(list.size + 1, response)
            }
        }
    }
}