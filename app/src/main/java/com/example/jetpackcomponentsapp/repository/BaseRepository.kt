package com.example.jetpackcomponentsapp.repository

import com.example.jetpackcomponentsapp.model.CustomModel

interface BaseRepository {

    public fun getItems() : List<CustomModel>

}