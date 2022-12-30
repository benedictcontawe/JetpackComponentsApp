package com.example.jetpackcomponentsapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<String> data = new MutableLiveData<String>();
    private final MutableLiveData<Integer> progressData = new MutableLiveData<Integer>();

    public LiveData<String> getData() {
        return data;
    }

    public void setData(String data){
        this.data.setValue(data);
    }

    public LiveData<Integer> getProgressData() {
        return progressData;
    }

    public void setProgressData(int progressData) {
        this.progressData.setValue(progressData);
    }
}