package com.example.jetpackcomponentsapp

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.room.CustomEntity
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    constructor(application: Application) : super(application) {
        repository = CustomRepository(application)
        //liveStandBy.setValue(true)
    }

    private val repository : CustomRepository
    private val liveUpdate : MutableLiveData<CustomModel?> = MutableLiveData<CustomModel?>(null)
    private val liveStandBy : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private val liveAddDialog : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private val _addTextState : MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    private val liveUpdateDialog : MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    private val _updateTextState : MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    //region Live Progress Bar Methods
    public suspend fun viewDidLoad() {
        liveStandBy.postValue(true)
    }

    public suspend fun viewWillAppear() {
        Log.d(TAG,"viewWillAppear")
        delay(500)
        liveStandBy.postValue(false)
    }

    public fun observeLoadState() : LiveData<Boolean> {
        return liveStandBy
    }
    //endregion
    //region Add Dialog Methods
    public fun setAddDialog(isShowed : Boolean) {
        liveAddDialog.setValue(isShowed)
    }

    public fun observeAddDialog() : LiveData<Boolean> {
        return liveAddDialog
    }

    val addTextState : State<TextFieldValue>
        get() = _addTextState

    fun onAddTextChanged() {
        _addTextState.value = TextFieldValue()
    }

    fun onAddTextChanged(newValue : TextFieldValue) {
        _addTextState.value = newValue
    }
    //endregion
    //region Update Dialog Methods
    public fun setUpdateDialog(isShowed : Boolean) { Coroutines.main(this@MainViewModel, {
        liveUpdateDialog.emit(isShowed)
    } ) }

    public fun observeUpdateDialog() : StateFlow<Boolean> {
        return liveUpdateDialog
    }

    val updateTextState : State<TextFieldValue>
        get() = _updateTextState

    fun onUpdateTextChanged() {
        liveUpdate.getValue()?.name?.let { oldName : String ->
            _updateTextState.value = TextFieldValue (
                oldName
            )

        }
    }

    fun onUpdateTextChanged(newValue : TextFieldValue) {
        _updateTextState.value = newValue
    }

    public fun setUpdate(model : CustomModel?) { Coroutines.io(this@MainViewModel, {
        Log.d(TAG, "setUpdate $model")
        viewDidLoad()
        liveUpdate.postValue(model)
        Log.d(TAG, "liveUpdate $liveUpdate")
        viewWillAppear()
    } ) }
    //endregion
    //region Room CRUD Methods Methods
    public fun insertItem() { Coroutines.io(this@MainViewModel, {
        viewDidLoad()
        repository.insert (
            ConvertList.toEntity (
                CustomModel ( _addTextState.value.text )
            )
        )
        viewWillAppear()
    } ) }

    public fun updateItem() { Coroutines.io(this@MainViewModel, {
        viewDidLoad()
        val old : CustomModel? = liveUpdate.getValue()
        repository.update (
            ConvertList.toEntity (
                CustomModel (
                    old?.id,
                    updateTextState.value.text,
                    old?.icon
                )
            )
        )
        viewWillAppear()
    }) }

    public fun deleteItem(model : CustomModel?) { Coroutines.io(this@MainViewModel, {
        viewDidLoad()
        if (model != null)
            repository.delete(
                ConvertList.toEntity(model)
            )
        viewWillAppear()
    } ) }

    public fun deleteAll() { Coroutines.io(this@MainViewModel, {
        viewDidLoad()
        repository.deleteAll()
        viewWillAppear()
    }) }

    public fun observeItems() : SharedFlow<PagingData<CustomModel>> {
        return ConvertList.toSharedFlowPagingDataModel(repository.getFlowPagingData(), viewModelScope)
    }
    //endregion
    override fun onCleared() {
        Coroutines.io(this@MainViewModel, {
            repository.onCLose()
        })
        super.onCleared()
    }
}