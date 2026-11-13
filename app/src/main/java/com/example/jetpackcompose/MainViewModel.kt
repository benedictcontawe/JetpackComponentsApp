package com.example.jetpackcompose

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CancellationException
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
    private val isRefreshing : MutableStateFlow<Boolean>

    constructor() {
        Log.d(TAG, "constructor")
        repository = Repository()
        list = mutableListOf<NasaHolderModel>()
        liveList = MutableLiveData<List<NasaHolderModel>>()
        isRefreshing = MutableStateFlow<Boolean>(false)
    }

    init {
        Log.d(TAG, "initialize")
    }

    public fun observeRefreshing() : StateFlow<Boolean> {
        return isRefreshing.asStateFlow<Boolean>()
    }
    // First load only: does nothing once pictures are loaded (also retries if the first load failed).
    public fun requestAPODIfEmpty() {
        if (list.isEmpty()) {
            requestAPOD()
        }
    }

    public fun requestAPOD() { // Ignore a new pull while the previous request is still running.
        if (isRefreshing.value) {
            return
        }
        isRefreshing.value = true
        Coroutines.io(this@MainViewModel, {
            try {
                val request : NasaRequestModel = NasaRequestModel(Constants.API_KEY, Constants.APPEND_COUNT)
                val responseList : List<NasaResponseModel> = repository.getAPOD(request)
                Log.d(TAG, "requestAPOD() size ${responseList.size}")
                val shownDates : Set<String> = list.map { model : NasaHolderModel -> model.date }.toSet()
                val newList : List<NasaHolderModel> = responseList
                    .filter { response : NasaResponseModel -> response.date.isNullOrBlank().not() }
                    .distinctBy { response : NasaResponseModel -> response.date }
                    .filterNot { response : NasaResponseModel -> response.date in shownDates }
                    .map { response : NasaResponseModel -> NasaHolderModel(response) }
                    .reversed() // Newest on top
                list.addAll(0, newList)
                liveList.postValue(list.toList())
            } catch (exception : CancellationException) {
                Log.e(TAG, "requestAPOD() CancellationException ", exception)
                throw exception
            } catch (exception : Exception) { // No internet, timeout, bad response, etc. Keep the current list instead of crashing.
                Log.e(TAG, "requestAPOD() Exception", exception)
            } finally {
                isRefreshing.value = false
            }
        })
    }

    public fun observeAPOD() : LiveData<List<NasaHolderModel>> {
        return liveList
    }
}