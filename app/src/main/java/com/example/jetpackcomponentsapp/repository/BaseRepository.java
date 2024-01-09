package com.example.jetpackcomponentsapp.repository;

import com.example.jetpackcomponentsapp.model.CustomModel;

import java.util.List;

public interface BaseRepository {
    public List<CustomModel> getItems();
}