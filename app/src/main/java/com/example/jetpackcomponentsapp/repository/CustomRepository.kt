package com.example.jetpackcomponentsapp.repository

import com.example.jetpackcomponentsapp.realm.CustomDatabase
import com.example.jetpackcomponentsapp.realm.CustomObject
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow

class CustomRepository : BaseRepository {


    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null

        fun getInstance() : CustomRepository {
            return INSTANCE ?: CustomRepository()
        }
    }

    init {
        CustomDatabase.onOpen()
    }
    //region CRUD Operation
    override public suspend fun insert(customObject: CustomObject) {
        CustomDatabase.writeAsync(customObject)
    }

    override public suspend fun update(customObject: CustomObject) {
        CustomDatabase.update(customObject)
    }

    override public suspend fun delete(customObject: CustomObject) {
        CustomDatabase.delete(customObject)
    }

    override public suspend fun deleteAll() {
        CustomDatabase.deleteAll()
    }

    override public suspend fun getAll() : RealmResults<CustomObject>? {
        return CustomDatabase.query()
    }

    override public suspend fun getAllFlow() : Flow<ResultsChange<CustomObject>>? {
        return CustomDatabase.queryFlow()
    }
    //endregion
    override public suspend fun onCLose() {
        CustomDatabase.onCLose()
    }
}