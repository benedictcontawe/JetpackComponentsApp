package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CustomRepository : BaseRepository {

    companion object {
        private var customRepository : CustomRepository? = null

        fun getInstance(application: Application) :  CustomRepository? {
            /*
            if (customRepository == null) {
                customRepository ?: CustomRepository(application)
            }
            return customRepository
             */
            return customRepository ?: CustomRepository(application)
        }
    }

    constructor(application : Application)

    fun getFullname(customModel: CustomModel) : LiveData<String> {
        val sample : MutableLiveData<String> = MutableLiveData()
        sample.value = "Your Full name is ${customModel.firstName} ${customModel.lastName}"
        return sample
    }

    fun getErrorName() : LiveData<String> {
        val sample : MutableLiveData<String> = MutableLiveData()
        sample.value = "Your Full name is invalid"
        return sample
    }

    override fun insert() {

    }

    override fun update() {

    }

    override fun delete() {

    }

    override fun deleteAll() {

    }
}