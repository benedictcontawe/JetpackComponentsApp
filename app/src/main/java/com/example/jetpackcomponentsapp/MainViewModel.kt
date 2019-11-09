package com.example.jetpackcomponentsapp

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private lateinit var data : String
    private var progressData : Int? = null

    fun getData() : String {
        return data
    }

    fun setData(data : String) {
        this.data = data
    }

    fun getProgressData() : Int? {
        return progressData
    }

    fun setProgressData(progressData : Int) {
        this.progressData = progressData
    }
}