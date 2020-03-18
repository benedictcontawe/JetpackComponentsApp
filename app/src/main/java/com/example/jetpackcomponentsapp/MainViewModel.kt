package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.util.ConvertList
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
        customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 0")))
        customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 1")))
        customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 2")))
        customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 3")))
        customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 4")))
        customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 5")))
        customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 6")))
        customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 7")))
        customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 8")))
    }

    fun setUpdate(item : CustomModel) {
        liveUpdate.value = item
    }

    fun getUpdate() : LiveData<CustomModel> {
        return liveUpdate
    }

    fun insertItem(item : CustomModel) {
        customRepository.insert(
                ConvertList.toEntity(item)
        )
    }

    fun updateItem() {
        liveUpdate.value?.let {
            customRepository.update(
                ConvertList.toEntity(it)
            )
        }
    }

    fun deleteItem(item : CustomModel) {
        customRepository.delete(
                ConvertList.toEntity(item)
        )
    }

    fun deleteAll() {
        customRepository.deleteAll()
    }

    fun getItems() : LiveData<MutableList<CustomModel>> {
        //return  liveList
        return ConvertList.toLiveDataListModel(
                customRepository.getAll()
        )
    }
}