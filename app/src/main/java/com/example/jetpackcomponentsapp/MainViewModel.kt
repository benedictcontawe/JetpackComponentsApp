package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.jetpackcomponentsapp.model.CustomHolderModel
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.util.Coroutines
import com.example.jetpackcomponentsapp.util.LoadStateEnum
import kotlinx.coroutines.flow.*

public class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG = MainViewModel::class.java.getSimpleName()
    }

    private val repository : CustomRepository
    private val liveUpdate : MutableStateFlow<CustomHolderModel>
    private val liveLoadState : MutableSharedFlow<LoadStateEnum>

    constructor(application : Application) : super(application) {
        repository = CustomRepository(application.getApplicationContext())
        liveUpdate = MutableStateFlow(CustomHolderModel())
        liveLoadState = MutableSharedFlow(/*LoadStateEnum.NOT_LOADING*/)
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

    public fun setOnLoadingState() { Coroutines.io (this@MainViewModel, {
        liveLoadState.emit(LoadStateEnum.LOADING)
    }) }

    public fun setDidLoadState() { Coroutines.io (this@MainViewModel, {
        liveLoadState.emit(LoadStateEnum.NOT_LOADING)
    }) }

    public fun observeLoadState() : SharedFlow<LoadStateEnum> {
        return liveLoadState.asSharedFlow()
    }

    public suspend fun observeItems() : SharedFlow<PagingData<CustomHolderModel>> {
        return ConvertList.toSharedFlowPagingDataModel(repository.getFlowPagingData(), viewModelScope)
    }
}