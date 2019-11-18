package com.example.jetpackcomponentsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val data = MutableLiveData<CustomModel>()
    private val Data : LiveData<String> = Transformations.map<CustomModel, String>(data,::processData)

    private fun processData(customModel: CustomModel) : String =
        if (customModel.firstName.isNullOrEmpty()|| customModel.lastName.isNullOrEmpty()) {
            "Your Full name is invalid"
        }
        else {
            "Your Full name is ${customModel.firstName} ${customModel.lastName}"
        }

    fun getData() : LiveData<String> {
        return Data
    }

    fun setData(customModel: CustomModel) : MainViewModel =
            //This will be use for the back end like calling retrofit data or
        apply {
            data.setValue(customModel)
        }
}

/*
    //Holds the location in a the form of [lat,lng], as is convenient for the UI:
    LiveData<double[]> locationLiveD = ...;

    // Creates a string format from the location, as Repository requires
    LiveData<String> locationStrLiveD =
    Transformations.map(locationLiveD, newLocation ->
            String.format("%0s,%1s", newLocation[0], newLocation[1]));

    //Gets the venue DataModels at the location from Repository
    LiveData<List<VenueData>> dataModelsLiveD = Transformations.switchMap(locationStrLiveD,
            newLocationStr -> repository.getVenues(newLocationStr));

    //Transforms DataModels to ViewModels
    LiveData<List<VenueViewModel>> viewModelsLiveD =
    Transformations.map(dataModelsLiveD, newData -> createVenuesViewModel(newData));
*/