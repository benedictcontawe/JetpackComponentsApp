package com.example.jetpackcomponentsapp.repository

import androidx.lifecycle.LiveData
import com.example.jetpackcomponentsapp.room.CustomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface BaseRepository {

    public suspend fun  insert(customEntity: CustomEntity)

    public suspend fun  update(customEntity: CustomEntity)

    public suspend fun  delete(customEntity: CustomEntity)

    public suspend fun  deleteAll()

    public fun getAll() : Flow<List<CustomEntity>>
}