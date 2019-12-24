package com.example.jetpackcomponentsapp.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.room.CustomDAO
import com.example.jetpackcomponentsapp.room.CustomDatabase

class CustomRepository(applicationContext: Application) : BaseRepository {

    private lateinit var customDao: CustomDAO

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
    override fun insert(customModel: CustomModel) {
        AsyncTask.execute {
            customDao.insert(
                    ConvertList.toEntity(customModel)
            )
        }
    }

    override fun update(customModel: CustomModel) {
        //customDao.update(customEntity)
        AsyncTask.execute {
            customDao.update(
                    ConvertList.toEntity(customModel)
            )
        }
    }

    override fun delete(customModel: CustomModel) {
        println("${customModel.id}")
        AsyncTask.execute {
            customDao.delete(
                    customModel.id
            )
        }
    }

    override fun deleteAll() {
        AsyncTask.execute {
            customDao.deleteAll()
        }
    }

    fun getAll() : LiveData<MutableList<CustomModel>> {
        return ConvertList.toLiveDataListModel(
                customDao.getAll()
        )
    }
    //endregion
}