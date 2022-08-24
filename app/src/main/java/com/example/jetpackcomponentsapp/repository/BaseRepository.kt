package com.example.jetpackcomponentsapp.repository

import com.example.jetpackcomponentsapp.realm.CustomObject
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    public suspend fun insert(customObject : CustomObject)

    public suspend fun insert(customObjects : RealmList<CustomObject>)

    public suspend fun  update(customObject: CustomObject)

    public suspend fun  delete(customObject: CustomObject)

    public suspend fun  deleteAll()

    public fun getFirstOrNull(id : Int) : CustomObject?

    public fun getFirstOrNull(name : String) : CustomObject?

    public fun getFirstOrNull(customObject : CustomObject) : CustomObject?

    public suspend fun getFirstOrNullFlow(id : Int) : Flow<ResultsChange<CustomObject>>?

    public suspend fun getFirstOrNullFlow(name : String) : Flow<ResultsChange<CustomObject>>?

    public suspend fun getAll() : RealmResults<CustomObject>?

    public suspend fun getAllFlow() : Flow<ResultsChange<CustomObject>>?

    public fun getMax() : Int

    public suspend fun  onCLose()
}