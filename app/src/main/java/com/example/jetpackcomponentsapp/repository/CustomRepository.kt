package com.example.jetpackcomponentsapp.repository

import android.app.Application
import com.example.jetpackcomponentsapp.room.CustomDatabase
import com.example.jetpackcomponentsapp.room.CustomEntity

class CustomRepository : BaseRepository {

    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null

        fun getInstance(applicationContext : Application) : CustomRepository {
            return INSTANCE ?: CustomRepository(applicationContext)
        }
    }

    constructor(applicationContext : Application) {
        val db = CustomDatabase.getInstance(applicationContext)
    }

    //region CRUD Operation
    override fun insert(customEntity: CustomEntity) {

    }

    override fun update(customEntity: CustomEntity) {

    }

    override fun delete(customEntity: CustomEntity) {

    }

    override fun deleteAll() {

    }
    //endregion
}