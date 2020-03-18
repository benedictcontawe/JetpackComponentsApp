package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.web.CountryResponseModel

class MainViewModel : AndroidViewModel {

    private lateinit var customRepository : CustomRepository

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository.getInstance(application)
    }

    fun requestCountry() : LiveData<List<CountryResponseModel>> {
        return customRepository.requestCountryDetails()
    }
}