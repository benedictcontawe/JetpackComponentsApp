package com.example.jetpackcomponentsapp.repository

import com.example.jetpackcomponentsapp.model.CustomModel


interface BaseRepository {

    fun  insert(customModel: CustomModel)

    fun  update(customModel: CustomModel)

    fun  delete(customModel: CustomModel)

    fun  deleteAll()
}