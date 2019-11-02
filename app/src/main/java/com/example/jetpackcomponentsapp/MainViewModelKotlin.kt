package com.example.jetpackcomponentsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModelKotlin : ViewModel() {
    private val data = MutableLiveData<CustomModel>()
    val Data : LiveData<String> = Transformations.map<CustomModel, String>(data,::processData)

    private fun processData(customModel: CustomModel) : String =
        if (customModel.firstName.isNullOrEmpty()|| customModel.lastName.isNullOrEmpty()) {
            "Your Full name is invalid"
        }
        else {
            "Your Full name is ${customModel.firstName} ${customModel.lastName}"
        }

    fun setData(customModel: CustomModel) : MainViewModelKotlin =
        apply {
            data.setValue(customModel)
        }
}