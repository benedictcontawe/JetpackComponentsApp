package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.repository.CustomRepository

class MainViewModel : AndroidViewModel {

    companion object {
        private lateinit var customRepository : CustomRepository
    }

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository.getInstance(application)
    }

    public fun getItemCount() : Int {
        return customRepository.getItems().size
    }

    public fun getItems() : List<CustomModel> {
        return customRepository.getItems()
    }
}