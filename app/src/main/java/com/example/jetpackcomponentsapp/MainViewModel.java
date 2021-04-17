package com.example.jetpackcomponentsapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.repository.CustomRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    static private CustomRepository customRepository;

    public MainViewModel(Application application) {
        super(application);
        customRepository = CustomRepository.getInstance(application);
    }

    public int getItemCount()  {
        return customRepository.getItems().size();
    }

    public List<CustomModel> getItems()  {
        return customRepository.getItems();
    }
}