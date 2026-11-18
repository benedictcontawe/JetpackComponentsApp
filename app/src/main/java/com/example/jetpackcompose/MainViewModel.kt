package com.example.jetpackcompose

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

public class MainViewModel : ViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    private val repository : Repository
    private val liveList : MutableStateFlow<List<NasaHolderModel>>
    private val isRefreshing : MutableStateFlow<Boolean>

    constructor() {
        Log.d(TAG, "constructor")
        repository = Repository()
        liveList = MutableStateFlow<List<NasaHolderModel>>(emptyList<NasaHolderModel>())
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
        if (liveList.value.isEmpty()) {
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
                liveList.update { currentList : List<NasaHolderModel> ->
                    val shownDates : Set<String> = currentList.map { model : NasaHolderModel -> model.date }.toSet()
                    val newList : List<NasaHolderModel> = responseList
                        .filter { response : NasaResponseModel -> response.date.isNullOrBlank().not() }
                        .distinctBy { response : NasaResponseModel -> response.date }
                        .filterNot { response : NasaResponseModel -> response.date in shownDates }
                        .map { response : NasaResponseModel -> NasaHolderModel(response) }
                        .reversed() // Newest on top
                    newList + currentList
                }
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

    public fun observeAPOD() : StateFlow<List<NasaHolderModel>> {
        return liveList.asStateFlow<List<NasaHolderModel>>()
    }
}