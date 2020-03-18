package com.example.jetpackcomponentsapp.repository

import androidx.lifecycle.LiveData
import com.example.jetpackcomponentsapp.room.CustomEntity

interface BaseRepository {

    suspend fun  insert(customEntity: CustomEntity)

    suspend fun  update(customEntity: CustomEntity)

    suspend fun  delete(customEntity: CustomEntity)

    suspend fun  deleteAll()

    fun getAll() : LiveData<List<CustomEntity>>
}