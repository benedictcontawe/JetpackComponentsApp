package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.*

class MainViewModel : AndroidViewModel {

    private lateinit var repository : CustomRepository
    private val data = MutableLiveData<CustomModel>()

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

    fun getData() : LiveData<String> = Transformations.switchMap(data,::processData)

    fun setData(customModel: CustomModel) : MainViewModel =
        apply {
            data.value = customModel //data.setValue(customModel)
        }
}