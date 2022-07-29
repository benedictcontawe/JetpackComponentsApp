package com.example.jetpackcomponentsapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.jetpackcomponentsapp.room.CustomDAO
import com.example.jetpackcomponentsapp.room.CustomDatabase
import com.example.jetpackcomponentsapp.room.CustomEntity

class CustomRepository(applicationContext: Application) : BaseRepository {

    private lateinit var customDao: CustomDAO

    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null

        fun getInstance(applicationContext : Application) : CustomRepository {
            return INSTANCE ?: CustomRepository(applicationContext)
        }
    }

    init {
        val database : CustomDatabase? = CustomDatabase.getInstance(applicationContext.applicationContext, "custom_database.db")
        customDao = database!!.customDao()
    }
    //region CRUD Operation
    override public suspend fun insert(customEntity: CustomEntity) {
        customDao.insert (
                customEntity
        )
    }

    override public suspend fun update(customEntity: CustomEntity) {
        customDao.update (
                customEntity
        )
    }

    override public suspend fun delete(customEntity: CustomEntity) {
        println("${customEntity.id}")
        customDao.delete (
                customEntity.id
        )
    }

    override public suspend fun deleteAll() {
        customDao.deleteAll()
    }

    override public fun getAll() : LiveData<List<CustomEntity>> {
        return customDao.getAll()
    }
    //endregion
}