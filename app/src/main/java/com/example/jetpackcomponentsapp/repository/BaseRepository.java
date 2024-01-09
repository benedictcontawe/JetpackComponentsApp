package com.example.jetpackcomponentsapp.repository;

import com.example.jetpackcomponentsapp.model.CustomModel;

public interface BaseRepository {

    public void insert(CustomModel customModel);

    public void update(CustomModel customModel);

    public void delete(CustomModel customModel);

    public void deleteAll();
}