package com.example.jetpackcomponentsapp.repository

import android.app.Application
import com.example.jetpackcomponentsapp.room.CustomDAO
import com.example.jetpackcomponentsapp.room.CustomDatabase
import com.example.jetpackcomponentsapp.room.CustomEntity
import kotlinx.coroutines.flow.Flow

class CustomRepository(applicationContext: Application) : BaseRepository {

    private var customDao : CustomDAO?
    private var database : CustomDatabase?

    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null

        fun getInstance(applicationContext : Application) : CustomRepository {
            return INSTANCE ?: CustomRepository(applicationContext)
        }
    }

    init {
        database = CustomDatabase.getInstance(applicationContext.applicationContext)
        customDao = database?.customDao()
    }
    //region CRUD Operation
    override public suspend fun insert(customEntity: CustomEntity) {
        customDao?.insert(
                customEntity
        )
    }

    override public suspend fun update(customEntity: CustomEntity) {
        customDao?.update(
                customEntity
        )
    }

    override public suspend fun delete(customEntity: CustomEntity) {
        println("${customEntity.id}")
        customDao?.delete(
                customEntity.id
        )
    }

    override public suspend fun deleteAll() {
        customDao?.deleteAll()
    }

    override public fun getAll() : Flow<List<CustomEntity>>? {
        return customDao?.getAll()
    }
    //endregion
    override suspend fun onCLose() {
        database?.onCLose()
        database?.onDestroy()
    }
}