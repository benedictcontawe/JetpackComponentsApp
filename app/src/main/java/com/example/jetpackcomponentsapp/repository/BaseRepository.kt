package com.example.jetpackcomponentsapp.repository

import com.example.jetpackcomponentsapp.room.CustomEntity
import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    public suspend fun  insert(customEntity: CustomEntity)

    public suspend fun  update(customEntity: CustomEntity)

    public suspend fun  delete(customEntity: CustomEntity)

    public suspend fun  deleteAll()

    public fun getAll() : Flow<List<CustomEntity>>
}