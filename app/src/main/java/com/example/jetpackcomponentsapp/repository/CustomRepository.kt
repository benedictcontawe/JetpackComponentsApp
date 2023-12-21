package com.example.jetpackcomponentsapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.jetpackcomponentsapp.room.CustomDAO
import com.example.jetpackcomponentsapp.room.CustomDatabase
import com.example.jetpackcomponentsapp.room.CustomEntity

class CustomRepository(applicationContext : Application) : BaseRepository {

    companion object {
        private val TAG : String = CustomRepository::class.java.getSimpleName()

        @Volatile private var INSTANCE  : CustomRepository? = null

        fun getInstance(applicationContext : Application) : CustomRepository {
            return INSTANCE ?: CustomRepository(applicationContext)
        }
    }

    private var customDao : CustomDAO?

    init {
        val database : CustomDatabase? = CustomDatabase.getInstance(applicationContext.getApplicationContext(), "custom_database.db")
        customDao = database?.customDao()
    }

    //region CRUD Operation
    override public fun insert(customEntity : CustomEntity) {
        Log.d(TAG, "insert ${customEntity}")
        customDao?.insert(
            customEntity
        )
    }

    override public fun update(customEntity : CustomEntity) {
        Log.d(TAG, "update ${customEntity}")
        customDao?.update(
            customEntity
        )
    }

    override public fun delete(customEntity : CustomEntity) {
        Log.d(TAG, "delete ${customEntity}")
        customDao?.delete(
            customEntity.id
        )
    }

    override public fun deleteAll() {
        Log.d(TAG, "deleteAll")
        customDao?.deleteAll()
    }

    fun getAll() : LiveData<List<CustomEntity>> {
        return customDao!!.getAll()
    }
    //endregion
}