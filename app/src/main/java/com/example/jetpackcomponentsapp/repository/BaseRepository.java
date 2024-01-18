package com.example.jetpackcomponentsapp.repository;

import androidx.lifecycle.LiveData;
import com.example.jetpackcomponentsapp.model.CustomModel;
import java.util.List;

public interface BaseRepository {

    public void insert(CustomModel customModel);

    public void update(CustomModel customModel);

    public void delete(CustomModel customModel);

    public void deleteAll();

    public LiveData<List<CustomModel>> getAll();
}