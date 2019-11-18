package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.*

class MainViewModel : AndroidViewModel {

    private lateinit var repository : CustomRepository
    private val data = MutableLiveData<CustomModel>()
    private val Data : LiveData<String> = Transformations.switchMap(data,::processData)

    constructor(application: Application) : super(application) {
        repository = CustomRepository.getInstance(application)!!
    }

    private fun processData(customModel: CustomModel) : LiveData<String> =
            if (customModel.firstName.isNullOrEmpty() || customModel.lastName.isNullOrEmpty()) {
                repository.getErrorName()
            }
            else {
                repository.getFullname(customModel)
            }

    //fun getData() : LiveData<String> = Data
    fun getData() : LiveData<String> {
        return Data
    }

    fun setData(customModel: CustomModel) : MainViewModel =
        apply {
            data.value = customModel //data.setValue(customModel)
        }
}