package com.example.jetpackcomponentsapp.repository

import com.example.jetpackcomponentsapp.model.CustomEntity

interface BaseRepository {

    public suspend fun  insert(customEntity: CustomEntity)

    public suspend fun  update(customEntity: CustomEntity)

    public suspend fun  delete(customEntity: CustomEntity)

    public suspend fun  deleteAll()

    public suspend fun getAll() : List<CustomEntity>

    public suspend fun  onCLose()
}