package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.repository.CustomRepository

class MainViewModel : AndroidViewModel {

    private lateinit var customRepository : CustomRepository
    private lateinit var liveList : MutableLiveData<MutableList<CustomModel>>
    private lateinit var liveUpdate : MutableLiveData<CustomModel>

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository.getInstance(application)
        liveList = MutableLiveData()
        liveUpdate = MutableLiveData()
    }
    @Deprecated("For Static Data")
    fun setItems() {
        customRepository.deleteAll()
        customRepository.insert(CustomModel(0,"name 0"))
        customRepository.insert(CustomModel(1,"name 1"))
        customRepository.insert(CustomModel(2,"name 2"))
        customRepository.insert(CustomModel(3,"name 3"))
        customRepository.insert(CustomModel(4,"name 4"))
        customRepository.insert(CustomModel(5,"name 5"))
        customRepository.insert(CustomModel(6,"name 6"))
        customRepository.insert(CustomModel(7,"name 7"))
        customRepository.insert(CustomModel(8,"name 8"))
    }

    fun setUpdate(item : CustomModel) {
        liveUpdate.value = item
    }

    fun getUpdate() : LiveData<CustomModel> {
        return liveUpdate
    }

    fun insertItem(item : CustomModel) {
        customRepository.insert(item)
    }

    fun updateItem() {
        liveUpdate.value?.let {
            customRepository.update(it)
        }
    }

    fun deleteItem(item : CustomModel) {
        customRepository.delete(item)
    }

    fun deleteAll() {
        customRepository.deleteAll()
    }

    fun getItems() : LiveData<MutableList<CustomModel>> {
        //return  liveList
        return  customRepository.getAll()
    }
}