package com.example.jetpackcomponentsapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private String data;
    private Integer progressData;

    public String getData() {
        return data;
    }

    public void setData(String data){
        this.data = data;
    }

    public Integer getProgressData() {
        return progressData;
    }

    public void setProgressData(int progressData) {
        this.progressData = progressData;
    }
}