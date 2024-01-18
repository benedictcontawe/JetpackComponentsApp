package com.example.jetpackcomponentsapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.repository.BaseRepository;
import com.example.jetpackcomponentsapp.repository.CustomRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public static final String TAG = MainViewModel.class.getSimpleName();
    private final BaseRepository repository;
    private final MutableLiveData<List<CustomModel>> liveList;
    private final MutableLiveData<CustomModel> liveUpdate;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = CustomRepository.getInstance(application);
        liveList = new MutableLiveData();
        liveUpdate = new MutableLiveData<>();
    }

    @Deprecated
    public void setItems() {
        List<CustomModel> staticData = new ArrayList<>();
        staticData.clear();
        staticData.add(new CustomModel(0,"Name 0"));
        staticData.add(new CustomModel(1,"name 1"));
        staticData.add(new CustomModel(2,"name 2"));
        staticData.add(new CustomModel(3,"name 3"));
        staticData.add(new CustomModel(4,"name 4"));
        staticData.add(new CustomModel(5,"name 5"));
        staticData.add(new CustomModel(6,"name 6"));
        staticData.add(new CustomModel(7,"name 7"));
        staticData.add(new CustomModel(8,"name 8"));
        liveList.setValue(staticData);
    }

    public void setUpdate(CustomModel item) {
        liveUpdate.setValue(item);
    }

    public LiveData<CustomModel> getUpdate() {
        return liveUpdate;
    }

    public void insertItem(CustomModel item) {
        repository.insert(item);
    }

    public void updateItem() {
        repository.update(liveUpdate.getValue());
    }
    public void deleteItem(CustomModel item) {
        repository.delete(item);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<CustomModel>> getItems() {
        //return liveList;
        return repository.getAll();
    }
}