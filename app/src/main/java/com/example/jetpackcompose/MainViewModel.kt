package com.example.jetpackcompose

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

public class MainViewModel : ViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    private val repository : Repository
    private val list : MutableList<NasaHolderModel>
    private val liveList : MutableLiveData<List<NasaHolderModel>>
    private val isLoading : MutableStateFlow<Boolean>

    constructor() {
        Log.d(TAG, "constructor")
        repository = Repository()
        list = mutableListOf<NasaHolderModel>()
        liveList = MutableLiveData<List<NasaHolderModel>>()
        isLoading = MutableStateFlow<Boolean>(false)
    }

    init {
        Log.d(TAG, "initialize")
    }

    public fun observeLoading() : StateFlow<Boolean> {
        return isLoading.asStateFlow<Boolean>()
    }

    public fun requestAPOD() { Coroutines.default(this@MainViewModel, {
        isLoading.value = true
        val request : NasaRequestModel = NasaRequestModel(Constants.API_KEY, list.size + 5)
        val responseList : List<NasaResponseModel> = repository.getAPOD(request)
        list.clear()
        Log.d(TAG, "requestAPOD() size ${responseList.size}")
        responseList.forEach { response -> Log.d(TAG, "Response $response")
            list.add(NasaHolderModel(list.size + 1, response))
        }
        liveList.postValue(list.reversed())
        isLoading.value = false
    } ) }

    public fun observeAPOD() : LiveData<List<NasaHolderModel>> {
        return liveList
    }
}