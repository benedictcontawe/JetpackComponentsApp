package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.flow.*

class MainViewModel : AndroidViewModel {

    private val customRepository : CustomRepository
    private val liveUpdate : MutableStateFlow<CustomModel>

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository.getInstance(application)
        liveUpdate = MutableStateFlow(CustomModel())
    }

    @Deprecated("For Static Data")
    fun setItems() { Coroutines.default(this@MainViewModel, {
        customRepository.deleteAll()
        for (index in 0 until 500) {
            customRepository.insert(ConvertList.toEntity(CustomModel(index, "name $index")))
        }
    }) }

    fun setUpdate(item: CustomModel) { Coroutines.io(this@MainViewModel) {
        liveUpdate.emit(item)
    } }

    fun getUpdate(): StateFlow<CustomModel> {
        return liveUpdate
    }

    fun insertItem(item: CustomModel) { Coroutines.io(this@MainViewModel, {
        customRepository.insert(
            ConvertList.toEntity(item)
        )
    }) }

    fun updateItem(updated: String) { Coroutines.io(this@MainViewModel, {
        liveUpdate.value.name = updated
        customRepository.update(
            ConvertList.toEntity(liveUpdate.value)
        )
    }) }

    fun deleteItem(item: CustomModel) { Coroutines.io(this@MainViewModel, {
        customRepository.delete(
            ConvertList.toEntity(item)
        )
    }) }

    fun deleteAll() { Coroutines.io(this@MainViewModel, {
            customRepository.deleteAll()
    }) }

    suspend fun getItems() : List<CustomModel> {
        return ConvertList.toListModel(customRepository.getAll())
    }

    override fun onCleared() {
        Coroutines.io(this@MainViewModel, {
            customRepository.onCLose()
        })
        super.onCleared()
    }
}