package com.example.jetpackcomponentsapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.jetpackcomponentsapp.model.CustomModel
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
        val database = CustomDatabase.getInstance(applicationContext.applicationContext)
        customDao = database!!.customDao()
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

    fun getAll() : LiveData<MutableList<CustomModel>> {
        val localList : LiveData<List<CustomEntity>> = customDao.getAll()
        val requesList : LiveData<MutableList<CustomModel>> =
                Transformations.map<
                        List<CustomEntity>, //localList Data Type
                        MutableList<CustomModel> //requesList Data Type
                        >(
                        localList,
                        ::convertList
                        )

        return requesList
    }

    private fun convertList(customEntity: List<CustomEntity>) : MutableList<CustomModel> {
        val itemList = mutableListOf<CustomModel>()
        customEntity.map { itemList.add(CustomModel(it.id?:0, it.name?:"")) }

        return itemList
    }
}