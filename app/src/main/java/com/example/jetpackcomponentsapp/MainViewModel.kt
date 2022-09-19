package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.room.CustomEntity
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.flow.*

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG = MainViewModel::class.java.getSimpleName()
    }

    private val customRepository : CustomRepository
    private val liveUpdate : MutableStateFlow<CustomModel>

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository(application)
        liveUpdate = MutableStateFlow(CustomModel())
    }

    fun setUpdate(item : CustomModel?) { Coroutines.io(this@MainViewModel) {
        if (item != null)
            liveUpdate.emit(item)
    } }

    fun observeUpdate() : StateFlow<CustomModel> {
        return liveUpdate
    }

    fun insertItem(item : CustomModel) { Coroutines.io(this@MainViewModel, {
        customRepository.insert(
            ConvertList.toEntity(item)
        )
    }) }

    fun updateItem(updated : String) { Coroutines.io(this@MainViewModel, {
        val old : CustomModel = liveUpdate.value
        customRepository.update(
            ConvertList.toEntity(CustomModel(old.id, updated, old.icon))
        )
    }) }

    fun deleteItem(item : CustomModel?) { Coroutines.io(this@MainViewModel, {
        if (item != null)
            customRepository.delete(
                ConvertList.toEntity(item)
            )
    }) }

    fun deleteAll() { Coroutines.io(this@MainViewModel, {
        customRepository.deleteAll()
    }) }

    suspend fun observeItems() : SharedFlow<List<CustomModel>> {
        return ConvertList.toSharedFlowListModel(customRepository.getAll() ?: emptyFlow<List<CustomEntity>>(), viewModelScope)
    }

    override fun onCleared() {
        Coroutines.io(this@MainViewModel, {
            customRepository.onCLose()
        })
        super.onCleared()
    }
}