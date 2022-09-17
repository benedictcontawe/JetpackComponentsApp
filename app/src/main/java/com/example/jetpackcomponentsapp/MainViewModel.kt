package com.example.jetpackcomponentsapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

public class MainViewModel : ViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    private val repository : Repository
    private val list : MutableList<NasaHolderModel>
    private val liveList : MutableLiveData<List<NasaHolderModel>>

    constructor() {
        repository = Repository()
        list = mutableListOf<NasaHolderModel>()
        liveList = MutableLiveData<List<NasaHolderModel>>()
    }

    public fun requestAPOD() { Coroutines.default(this@MainViewModel, {
        val request : NasaRequestModel = NasaRequestModel(Constants.API_KEY, list.size + 5)
        val responseList : List<NasaResponseModel> = repository.getAPOD(request)
        list.clear()
        Log.d(TAG, "getAPOD() size ${responseList.size}")
        responseList.forEach { response -> Log.d(TAG, "Response $response")
            list.add(NasaHolderModel(list.size + 1, response))
        }
        liveList.postValue(list.reversed())
    } ) }

    public fun observeAPOD() : LiveData<List<NasaHolderModel>> {
        return liveList
    }
}