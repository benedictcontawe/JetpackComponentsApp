package com.example.databindingmvvmapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> data = new MutableLiveData<>();
    private MutableLiveData<Integer> progressData = new MutableLiveData<>();

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
