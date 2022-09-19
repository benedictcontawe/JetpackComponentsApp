package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.jetpackcomponentsapp.model.CustomHolderModel
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.flow.*

public class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG = MainViewModel::class.java.getSimpleName()
    }

    private val repository : CustomRepository
    private val liveUpdate : MutableStateFlow<CustomHolderModel>

    constructor(application : Application) : super(application) {
        repository = CustomRepository(application.getApplicationContext())
        liveUpdate = MutableStateFlow(CustomHolderModel())
    }

    fun setUpdate(item : CustomHolderModel?) { Coroutines.io(this@MainViewModel) {
        if (item != null)
            liveUpdate.emit(item)
    } }

    fun observeUpdate() : StateFlow<CustomHolderModel> {
        return liveUpdate.asStateFlow()
    }

    fun insertItem(item : CustomHolderModel) { Coroutines.io (this@MainViewModel, {
        repository.insert(
            ConvertList.toEntity(item)
        )
    }) }

    fun updateItem(updated : String) { Coroutines.io(this@MainViewModel, {
        val old : CustomHolderModel = liveUpdate.value
        repository.update(
            ConvertList.toEntity(CustomHolderModel(old.id, updated, old.icon))
        )
    }) }

    fun deleteItem(item : CustomHolderModel?) { Coroutines.io (this@MainViewModel, {
        repository.delete(
            ConvertList.toEntity(item!!)
        )
    }) }

    fun deleteAll() { Coroutines.io (this@MainViewModel, {
        repository.deleteAll()
    }) }

    public suspend fun observeItems() : SharedFlow<PagingData<CustomHolderModel>> {
        return ConvertList.toSharedFlowPagingDataModel(repository.getFlowPagingData(), viewModelScope)
    }
}