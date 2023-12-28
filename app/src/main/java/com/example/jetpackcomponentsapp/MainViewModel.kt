package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.util.Coroutines

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    private val repository : CustomRepository

    constructor(application: Application) : super(application) {
        repository = CustomRepository.getInstance(application)
    }

    fun update(booleanKey : Boolean) {
        Coroutines.io(this@MainViewModel) {
            repository.update(booleanKey)
        }
    }

    fun update(stringKey : String) {
        Coroutines.io(this@MainViewModel) {
            repository.update(stringKey)
        }
    }

    fun update(integerKey : Int) {
        Coroutines.io(this@MainViewModel) {
            repository.update(integerKey)
        }
    }

    fun update(doubleKey : Double) {
        Coroutines.io(this@MainViewModel) {
            repository.update(doubleKey)
        }
    }

    fun update(longKey : Long) {
        Coroutines.io(this@MainViewModel) {
            repository.update(longKey)
        }
    }

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
}