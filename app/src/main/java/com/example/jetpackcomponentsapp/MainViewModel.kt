package com.example.jetpackcomponentsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    //TODO: Finish code for Transformations.switchMap
    private val data = MutableLiveData<CustomModel>()
    private val Data : LiveData<String> = Transformations.map<CustomModel, String>(data,::processData)

    private fun processData(customModel: CustomModel) : String =
        if (customModel.firstName.isNullOrEmpty()|| customModel.lastName.isNullOrEmpty()) {
            "Your Full name is invalid"
        }
        else {
            "Your Full name is ${customModel.firstName} ${customModel.lastName}"
        }

    fun getData() : LiveData<String> {
        return Data
    }

    fun setData(customModel: CustomModel) : MainViewModel =
        apply {
            data.setValue(customModel)
        }
}