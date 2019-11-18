package com.example.jetpackcomponentsapp;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CustomRepository implements BaseRepository {

    private static CustomRepository customRepository;

    public  static CustomRepository getInstance(Application application) {
        if (customRepository == null) {
            customRepository = new CustomRepository(application);
        }
        return customRepository;
    }

    public CustomRepository(Application application) {

    }

    LiveData<String> getFullname(CustomModel customModel) {
        MutableLiveData<String> sample = new MutableLiveData();
        sample.setValue("Your Full name is " + customModel.firstName + " " + customModel.lastName);
        return  sample;
    }

    LiveData<String> getErrorName() {
        MutableLiveData<String> sample = new MutableLiveData();
        sample.setValue("Your Full name is invalid");
        return  sample;
    }

    @Override
    public void insert() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void deleteAll() {

    }
}
