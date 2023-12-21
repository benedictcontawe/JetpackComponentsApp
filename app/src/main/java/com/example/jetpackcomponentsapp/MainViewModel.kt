package com.example.jetpackcomponentsapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.jetpackcomponentsapp.model.CustomHolderModel
import com.example.jetpackcomponentsapp.repository.BaseRepository
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.util.Coroutines
import com.example.jetpackcomponentsapp.util.LoadStateEnum
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    constructor(application: Application) : super(application) {
        repository = CustomRepository(application)
    }

    private val repository : BaseRepository
    private val liveUpdate : MutableStateFlow<CustomHolderModel?> = MutableStateFlow<CustomHolderModel?>(null)
    private val liveLoadState : MutableSharedFlow<LoadStateEnum> = MutableSharedFlow(/*LoadStateEnum.NOT_LOADING*/)

    public fun setUpdate(model : CustomHolderModel?) { Coroutines.io(this@MainViewModel, {
        Log.d(TAG, "setUpdate $model")
        liveUpdate.emit(model)
        Log.d(TAG, "liveUpdate $liveUpdate")
    } ) }

    public fun observeUpdate() : StateFlow<CustomHolderModel?> {
        return liveUpdate
    }

    public fun insertItem(model : CustomHolderModel) { Coroutines.io(this@MainViewModel, {
        repository.insert (
            ConvertList.toEntity(model)
        )
    } ) }

    public fun updateItem(updated : String) { Coroutines.io(this@MainViewModel, {
        val old : CustomHolderModel? = liveUpdate.value
        repository.update (
            ConvertList.toEntity(CustomHolderModel(old?.id, updated, old?.icon))
        )
    }) }

    public fun deleteItem(model : CustomHolderModel?) { Coroutines.io(this@MainViewModel, {
        if (model != null)
            repository.delete (
                ConvertList.toEntity(model)
            )
    } ) }

    public fun deleteAll() { Coroutines.io(this@MainViewModel, {
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

    override fun onCleared() {
        Coroutines.io(this@MainViewModel, {
            repository.onCLose()
        })
        super.onCleared()
    }
}