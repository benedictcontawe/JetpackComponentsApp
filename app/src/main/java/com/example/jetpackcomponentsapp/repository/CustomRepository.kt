package com.example.jetpackcomponentsapp.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.paging.*
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

    fun getAllDataSourceFactory() : DataSource.Factory<Int, CustomEntity> {
        return customDao.getAll()
    }

    fun getAllLivePageList() : LiveData<PagedList<CustomEntity>> {
        return customDao.getAll().toLiveData(
                getConfig()
        )
    }
    //endregion
    fun getConfig() : PagedList.Config {
        return Config(
                pageSize = 10,
                initialLoadSizeHint = 10, //default: page size * 3
                prefetchDistance = 10, //default: page size
                //maxSize = PagedList.Config.MAX_SIZE_UNBOUNDED,
                enablePlaceholders = false //default: true
        )
    }
}