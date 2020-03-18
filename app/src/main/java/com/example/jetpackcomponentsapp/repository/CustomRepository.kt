package com.example.jetpackcomponentsapp.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcomponentsapp.web.CountryAPI
import com.example.jetpackcomponentsapp.web.CountryResponseModel
import com.example.jetpackcomponentsapp.web.NetworkClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomRepository(applicationContext: Application) {

    private lateinit var context : Context
    private lateinit var countryAPI : CountryAPI

    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null

        fun getInstance(applicationContext : Application) : CustomRepository {
            return INSTANCE ?: CustomRepository(applicationContext)
        }
    }

    init {
        context = applicationContext

        countryAPI = NetworkClient.createService(CountryAPI::class.java)
    }

    fun requestCountryDetails() : LiveData<List<CountryResponseModel>> {
        val call = countryAPI.getCountryDetails()
        val countryResponseLiveList : MutableLiveData<List<CountryResponseModel>> = MutableLiveData()

        call.enqueue(object : Callback<List<CountryResponseModel>> {

            override fun onResponse(call : Call<List<CountryResponseModel>>, response : Response<List<CountryResponseModel>>) {
                if (!response.isSuccessful) {
                    Log.e("Test - CustomRepository","!response.isSuccessful")
                    return
                }
                Log.e("Test - CustomRepository","response.isSuccessful")
                countryResponseLiveList.value = response.body()!!
            }

            override fun onFailure(call : Call<List<CountryResponseModel>>, throwable : Throwable) {
                Log.e("Test - CustomRepository","Callback $throwable")
                countryResponseLiveList.value = null
            }
        })

        return countryResponseLiveList
    }
}