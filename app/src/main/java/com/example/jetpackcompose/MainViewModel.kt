package com.example.jetpackcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel {

    private var data : MutableLiveData<String> = MutableLiveData()
    private var progressData : MutableLiveData<Float> = MutableLiveData()

    constructor() {

    }

    public fun getData() : LiveData<String> {
        return data
    }

    public fun setData(data : String) {
        this.data.setValue(data)
    }

    public fun getProgressData() : LiveData<Float> {
        return progressData
    }

    public fun setProgressData(progressData : Float) {
        this.progressData.setValue(progressData)
    }

    override fun onCleared() {
        super.onCleared()
    }
}