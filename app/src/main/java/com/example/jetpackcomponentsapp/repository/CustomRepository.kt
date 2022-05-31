package com.example.jetpackcomponentsapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.jetpackcomponentsapp.room.CustomDAO
import com.example.jetpackcomponentsapp.room.CustomDatabase
import com.example.jetpackcomponentsapp.room.CustomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

class CustomRepository(applicationContext: Application) : BaseRepository {

    private lateinit var customDao : CustomDAO

    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null

        fun getInstance(applicationContext : Application) : CustomRepository {
            return INSTANCE ?: CustomRepository(applicationContext)
        }
    }

    init {
        val database : CustomDatabase? = CustomDatabase.getInstance(applicationContext.applicationContext)
        customDao = database!!.customDao()
    }

    //region CRUD Operation
    override suspend fun insert(customEntity: CustomEntity) {
        customDao.insert(
                customEntity
        )
    }

    override suspend fun update(customEntity: CustomEntity) {
        customDao.update(
                customEntity
        )
    }

    override suspend fun delete(customEntity: CustomEntity) {
        println("${customEntity.id}")
        customDao.delete(
                customEntity.id
        )
    }

    override suspend fun deleteAll() {
        customDao.deleteAll()
    }

    override fun getAll() : LiveData<List<CustomEntity>> {
        return customDao.getAll()
    }
    //endregion
}