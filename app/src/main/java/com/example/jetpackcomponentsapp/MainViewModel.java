package com.example.jetpackcomponentsapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<CustomModel> data = new MutableLiveData<>();
    LiveData<String> Data = Transformations.map(data,MainViewModel::processData);

    private static String processData(CustomModel customModel) {
        if (customModel.firstName.isEmpty()|| customModel.lastName.isEmpty()) {
            return "Your Full name is invalid";
        }
        else {
            return "Your Full name is " + customModel.firstName + " " + customModel.lastName;
        }
    }

    public LiveData<String> getData() {
        return Data;
    }

    void setData(CustomModel customModel) {
        data.setValue(customModel);
    }
}