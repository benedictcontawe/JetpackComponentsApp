package com.example.jetpackcomponentsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
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
            //This will be use for the back end like calling retrofit data or
        apply {
            data.setValue(customModel)
        }
}