package com.example.jetpackcomponentsapp.repository

import com.example.jetpackcomponentsapp.realm.CustomObject
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    public suspend fun insert(customObject : CustomObject)

    public suspend fun  update(customObject: CustomObject)

    public suspend fun  delete(customObject: CustomObject)

    public suspend fun  deleteAll()

    public suspend fun getAll() : RealmResults<CustomObject>?

    public suspend fun getAllFlow() : Flow<ResultsChange<CustomObject>>?

    public suspend fun  onCLose()
}