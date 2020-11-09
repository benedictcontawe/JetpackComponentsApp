package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.Dispatchers

class MainViewModel : AndroidViewModel {

    companion object {
        private lateinit var customRepository : CustomRepository
    }

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository.getInstance(application)
    }

    fun update(booleanKey : Boolean) {
        Coroutines.io(this@MainViewModel) {
            customRepository.update(booleanKey)
        }
    }

    fun update(stringKey : String) {
        Coroutines.io(this@MainViewModel) {
            customRepository.update(stringKey)
        }
    }

    fun update(integerKey : Int) {
        Coroutines.io(this@MainViewModel) {
            customRepository.update(integerKey)
        }
    }

    fun update(doubleKey : Double) {
        Coroutines.io(this@MainViewModel) {
            customRepository.update(doubleKey)
        }
    }

    fun update(longKey : Long) {
        Coroutines.io(this@MainViewModel) {
            customRepository.update(longKey)
        }
    }

    fun observeBoolean() : LiveData<Boolean> {
        return customRepository.getBoolean().asLiveData(viewModelScope.coroutineContext)
    }

    fun observeString() : LiveData<String> {
        return customRepository.getString().asLiveData(viewModelScope.coroutineContext)
    }

    fun observeInt() : LiveData<Int> {
        return customRepository.getInteger().asLiveData(viewModelScope.coroutineContext)
    }

    fun observeDouble() : LiveData<Double> {
        return customRepository.getDouble().asLiveData(viewModelScope.coroutineContext)
    }

    fun observeLong() : LiveData<Long> {
        return customRepository.getLong().asLiveData(viewModelScope.coroutineContext)
    }
}
/*
https://android-developers.googleblog.com/2020/09/prefer-storing-data-with-jetpack.html
https://developer.android.com/topic/libraries/architecture/datastore
https://medium.com/swlh/preferences-datastore-in-a-kotlin-way-4c4c41a906e1
https://dev.to/raystatic/welcome-preferences-datastore-88b
https://medium.com/scalereal/hello-datastore-bye-sharedpreferences-android-f46c610b81d5
https://medium.com/scalereal/hello-datastore-bye-sharedpreferences-android-part-2-proto-datastore-2716fbfd4783
https://blog.stylingandroid.com/datastore-basics/
 */