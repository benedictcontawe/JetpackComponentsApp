package com.example.jetpackcomponentsapp.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.jetpackcomponentsapp.room.CustomDAO
import com.example.jetpackcomponentsapp.room.CustomDatabase
import com.example.jetpackcomponentsapp.room.CustomEntity

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
    override fun insert(customEntity: CustomEntity) {
        AsyncTask.execute {
            customDao.insert(
                    customEntity
            )
        }
    }

    override fun update(customEntity: CustomEntity) {
        AsyncTask.execute {
            customDao.update(
                    customEntity
            )
        }
    }

    override fun delete(customEntity: CustomEntity) {
        println("${customEntity.id}")
        AsyncTask.execute {
            customDao.delete(
                    customEntity.id
            )
        }
    }

    override fun deleteAll() {
        AsyncTask.execute {
            customDao.deleteAll()
        }
    }

    fun getAll() : LiveData<List<CustomEntity>> {
        return customDao.getAll()
    }
    //endregion
}