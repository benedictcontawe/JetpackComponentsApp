package com.example.jetpackcomponentsapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends AndroidViewModel {

    private static CustomRepository repository;
    private MutableLiveData<CustomModel> data = new MutableLiveData<>();
    LiveData<String> Data = Transformations.switchMap(data,MainViewModel::processData);

    public MainViewModel(Application application) {
        super(application);
        repository = CustomRepository.getInstance(application);
    }

    private static LiveData<String> processData(CustomModel customModel) {
        if (customModel.firstName.isEmpty()|| customModel.lastName.isEmpty()) {
            return repository.getErrorName();
        }
        else {
            return repository.getFullname(customModel);
        }
    }

    public LiveData<String> getData() {
        return Data;
    }

    void setData(CustomModel customModel) {
        data.setValue(customModel);
    }
}