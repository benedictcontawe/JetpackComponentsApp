package com.example.jetpackcomponentsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.web.CountryResponseModel

class MainViewModel : ViewModel {

    private val customRepository : CustomRepository

    constructor() : super() {
        customRepository = CustomRepository.getInstance()
    }

    fun requestCountry() : LiveData<List<CountryResponseModel>> {
        return customRepository.requestCountryDetails()
    }
}