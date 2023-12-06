package com.example.jetpackcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel {

    private val data : MutableLiveData<String> = MutableLiveData()
    private val progressData : MutableLiveData<Float> = MutableLiveData()
    private val isSwitchChecked : MutableLiveData<Boolean> = MutableLiveData()

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

    public fun getSwitchChecked() : LiveData<Boolean> {
        return this.isSwitchChecked
    }

    public fun setSwitchChecked(isChecked : Boolean) {
        if (isChecked) {
            isSwitchChecked.setValue(true)
            setData("Switch is On")
        } else {
            isSwitchChecked.setValue(false)
            setData("Switch is Off")
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}