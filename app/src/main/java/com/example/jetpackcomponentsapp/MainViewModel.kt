package com.example.jetpackcomponentsapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.room.CustomEntity
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository(application)
    }

    private val customRepository : CustomRepository
    private val liveUpdate : MutableStateFlow<CustomModel?> = MutableStateFlow<CustomModel?>(null)

    public fun setUpdate(model : CustomModel?) { Coroutines.io(this@MainViewModel, {
        Log.d(TAG, "setUpdate $model")
        liveUpdate.emit(model)
        Log.d(TAG, "liveUpdate $liveUpdate")
    } ) }

    public fun observeUpdate() : StateFlow<CustomModel?> {
        return liveUpdate
    }

    public fun insertItem(model : CustomModel) { Coroutines.io(this@MainViewModel, {
        customRepository.insert (
            ConvertList.toEntity(model)
        )
    } ) }

    public fun updateItem(updated : String) { Coroutines.io(this@MainViewModel, {
        val old : CustomModel? = liveUpdate.value
        customRepository.update (
            ConvertList.toEntity(CustomModel(old?.id, updated, old?.icon))
        )
    }) }

    public fun deleteItem(model : CustomModel?) { Coroutines.io(this@MainViewModel, {
        if (model != null)
            customRepository.delete (
                ConvertList.toEntity(model)
            )
    } ) }

    public fun deleteAll() { Coroutines.io(this@MainViewModel, {
        customRepository.deleteAll()
    }) }

    public suspend fun observeItems() : SharedFlow<List<CustomModel>> {
        return ConvertList.toSharedFlowListModel(customRepository.getAll() ?: emptyFlow<List<CustomEntity>>(), viewModelScope)
    }

    override fun onCleared() {
        Coroutines.io(this@MainViewModel, {
            customRepository.onCLose()
        })
        super.onCleared()
    }
}