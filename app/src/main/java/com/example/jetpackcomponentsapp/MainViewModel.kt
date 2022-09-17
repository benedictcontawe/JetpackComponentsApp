package com.example.jetpackcomponentsapp

import android.util.Log
import androidx.lifecycle.ViewModel
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
    private val liveList : MutableSharedFlow<List<NasaHolderModel>>

    constructor() {
        repository = Repository()
        list = mutableListOf<NasaHolderModel>()
        liveList = MutableSharedFlow()
    }

    public fun requestAPOD() { Coroutines.default(this@MainViewModel, {
        val request : NasaRequestModel = NasaRequestModel(Constants.API_KEY, list.size + 5)
        val responseList : List<NasaResponseModel> = repository.getAPOD(request)
        list.clear()
        Log.d(TAG, "getAPOD() size ${responseList.size}")
        responseList.forEach { response -> Log.d(TAG, "Response $response")
            list.add(NasaHolderModel(list.size + 1, response))
        }
        liveList.emit(list.reversed())
    } ) }

    public suspend fun observeAPOD() : SharedFlow<PagingData<NasaHolderModel>> {
        val request : NasaRequestModel = NasaRequestModel(Constants.API_KEY, 100)
        return repository.getFlowAPOD(request).map { pagingDatum ->
            pagingDatum.map { response ->
                list.add(NasaHolderModel(0, response))
                NasaHolderModel(list.size + 1, response)
            }
        }.shareIn(viewModelScope, SharingStarted.Lazily)
    }
}