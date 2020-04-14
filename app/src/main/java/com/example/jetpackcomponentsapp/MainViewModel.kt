package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.switchMap

class MainViewModel : AndroidViewModel {

    private var repository: CustomRepository
    private val customModelLiveData = MutableLiveData<CustomModel>()
    private val stringLiveData: LiveData<String>

    constructor(application: Application) : super(application) {
        repository = CustomRepository.getInstance(application)!!


        // 1 - if data hold by customModelLiveData changes, second argument of switchMap() is invoked the resulting liveData assigned to stringLiveData, or
        // 2 - if the data hold by liveData which is returned from second argument of switchMap changes,
        // for example customModel in Room Db changes, then the resulting liveData assigned to stringLiveData
        stringLiveData = switchMap(customModelLiveData, { customModel -> processData(customModel) });
    }

    private fun processData(customModel: CustomModel): LiveData<String> =
            if (customModel.firstName.isNullOrEmpty() || customModel.lastName.isNullOrEmpty()) {
                repository.getErrorName()
            } else {
                repository.getFullname(customModel)
            }

    //fun getData() : LiveData<String> = Data
    fun getStringLiveData(): LiveData<String> {
        return stringLiveData
    }

    fun setCustomModelLiveData(customModel: CustomModel) {
        customModelLiveData.setValue(customModel)
    }
}