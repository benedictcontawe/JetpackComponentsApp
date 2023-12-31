package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.util.Coroutines

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    private val repository : CustomRepository
    private val liveSwitchChecked : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val _stringTextState : MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    private val _integerTextState : MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    private val _doubleTextState : MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    private val _longTextState : MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))

    constructor(application : Application) : super(application) {
        repository = CustomRepository.getInstance(application)
    }
    //region Switch Methods
    public fun getSwitchChecked() : LiveData<Boolean> {
        return this.liveSwitchChecked
    }

    public fun setSwitchChecked(isChecked : Boolean) {
        liveSwitchChecked.setValue(isChecked)
    }
    //endregion
    //region Text State Methods
    val stringTextState : State<TextFieldValue>
        get() = _stringTextState

    fun onStringTextChanged(newValue : TextFieldValue) {
        _stringTextState.value = newValue
    }

    val integerTextState : State<TextFieldValue>
        get() = _integerTextState

    fun onIntegerTextChanged(newValue : TextFieldValue) {
        _integerTextState.value = newValue
    }

    val doubleTextState : State<TextFieldValue>
        get() = _doubleTextState

    fun onDoubleTextChanged(newValue : TextFieldValue) {
        _doubleTextState.value = newValue
    }

    val longTextState : State<TextFieldValue>
        get() = _longTextState

    fun onLongTextChanged(newValue : TextFieldValue) {
        _longTextState.value = newValue
    }
    //endregion
    //region Update Methods
    fun update(booleanKey : Boolean?) {
        Coroutines.io(this@MainViewModel) {
            if (booleanKey != null) {
                repository.update(booleanKey)
            }
        }
    }

    fun update(stringKey : String?) {
        Coroutines.io(this@MainViewModel) {
            if (stringKey != null) {
                repository.update(stringKey)
            }
        }
    }

    fun update(integerKey : Int?) {
        Coroutines.io(this@MainViewModel) {
            if (integerKey != null) {
                repository.update(integerKey)
            }
        }
    }

    fun update(doubleKey : Double?) {
        Coroutines.io(this@MainViewModel) {
            if (doubleKey != null) {
                repository.update(doubleKey)
            }
        }
    }

    fun update(longKey : Long?) {
        Coroutines.io(this@MainViewModel) {
            if (longKey != null) {
                repository.update(longKey)
            }
        }
    }
    //endregion
    //region Observe Methods
    fun observeBoolean() : LiveData<Boolean?> {
        return repository.getBoolean().asLiveData(viewModelScope.coroutineContext)
    }

    fun observeString() : LiveData<String> {
        return repository.getString().asLiveData(viewModelScope.coroutineContext)
    }

    fun observeInt() : LiveData<Int?> {
        return repository.getInteger().asLiveData(viewModelScope.coroutineContext)
    }

    fun observeDouble() : LiveData<Double?> {
        return repository.getDouble().asLiveData(viewModelScope.coroutineContext)
    }

    fun observeLong() : LiveData<Long?> {
        return repository.getLong().asLiveData(viewModelScope.coroutineContext)
    }
    /*
    fun observeCustomModel() : LiveData<List<CustomModel>> {
        return repository.getCustomModel().asLiveData()
    }
    */
    //endregion
}