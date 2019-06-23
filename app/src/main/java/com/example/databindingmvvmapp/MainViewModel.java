package com.example.databindingmvvmapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> data = new MutableLiveData<>();

    public LiveData<String> getData() {
        return data;
    }

    public void setData(String data){
        this.data.setValue(data);
    }
}
