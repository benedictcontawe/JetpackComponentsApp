package com.example.jetpackcomponentsapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class MainViewModel extends AndroidViewModel {

    private static CustomRepository repository;
    private MutableLiveData<CustomModel> customModelLiveData = new MutableLiveData<>();
    LiveData<String> stringLiveData;

    public MainViewModel(Application application) {
        super(application);
        repository = CustomRepository.getInstance(application);

        // 1 -if data hold by customModelLiveData changes, second argument of switchMap() is invoked the resulting liveData assigned to stringLiveData, or
        // 2 - if the data hold by liveData which is returned from second argument of switchMap changes,
        // for example customModel in Room Db changes, then the resulting liveData assigned to stringLiveData
        stringLiveData = Transformations.switchMap(customModelLiveData, MainViewModel::processData);
    }

    private static LiveData<String> processData(CustomModel customModel) {
        if (customModel.firstName.isEmpty() || customModel.lastName.isEmpty()) {
            return repository.getErrorName();
        } else {
            return repository.getFullname(customModel);
        }
    }

    public LiveData<String> getStringLiveData() {
        return stringLiveData;
    }

    void setCustomModelLiveData(CustomModel customModel) {
        customModelLiveData.setValue(customModel);
    }
}