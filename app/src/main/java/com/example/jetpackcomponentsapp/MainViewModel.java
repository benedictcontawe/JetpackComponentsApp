package com.example.jetpackcomponentsapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.repository.CustomRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final CustomRepository repository;

    public MainViewModel(Application application) {
        super(application);
        repository = CustomRepository.getInstance(application);
    }

    public int getItemCount()  {
        return repository.getItems().size();
    }

    public List<CustomModel> getItems()  {
        return repository.getItems();
    }
}