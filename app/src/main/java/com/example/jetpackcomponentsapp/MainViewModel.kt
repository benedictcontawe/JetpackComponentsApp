package com.example.jetpackcomponentsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private lateinit var data : String
    private var progressData : Int? = null

    public fun getData() : String {
        return data
    }

    public fun setData(data : String) {
        this.data = data
    }

    public fun getProgressData() : Int? {
        return progressData
    }

    public fun setProgressData(progressData : Int) {
        this.progressData = progressData
    }
}