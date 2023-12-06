package com.example.jetpackcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var data : MutableLiveData<String> = MutableLiveData()
    private var progressData : MutableLiveData<Int> = MutableLiveData()

    public fun getData() : LiveData<String> {
        return data
    }

    public fun setData(data : String) {
        this.data.setValue(data)
    }

    public fun getProgressData() : LiveData<Int> {
        return progressData
    }

    public fun setProgressData(progressData : Int){
        this.progressData.setValue(progressData)
    }
}