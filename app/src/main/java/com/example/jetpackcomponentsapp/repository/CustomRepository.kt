package com.example.jetpackcomponentsapp.repository

import android.app.Application
import com.example.jetpackcomponentsapp.sqlite.DatabaseHelper
import com.example.jetpackcomponentsapp.model.CustomEntity

class CustomRepository(applicationContext: Application) : BaseRepository {

    private var databaseHelper : DatabaseHelper?

    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null

        fun getInstance(applicationContext : Application) : CustomRepository {
            return INSTANCE ?: CustomRepository(applicationContext)
        }
    }

    init {
        databaseHelper = DatabaseHelper(applicationContext.getApplicationContext(), "custom_database.db", null, 1)
    }
    //region CRUD Operation
    override public suspend fun insert(customEntity : CustomEntity) {
        databaseHelper?.insert(
            "custom_table",
            customEntity
        )
    }

    override public suspend fun update(customEntity : CustomEntity) {
        databaseHelper?.update(
            "custom_table",
            customEntity
        )
    }

    override public suspend fun delete(customEntity : CustomEntity) {
        databaseHelper?.delete(
            "custom_table",
            customEntity
        )
    }

    override public suspend fun deleteAll() {
        databaseHelper?.deleteAll(
            "custom_table"
        )
    }

    override public suspend fun getAll() : List<CustomEntity> {
        return databaseHelper?.getAll() ?: emptyList<CustomEntity>()
    }
    //endregion
    override suspend fun onCLose() {
        databaseHelper?.onClose()
    }
}