package com.example.jetpackcomponentsapp

import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jetpackcomponentsapp.room.CustomDAO
import com.example.jetpackcomponentsapp.room.CustomDatabase
import com.example.jetpackcomponentsapp.room.CustomEntity
import kotlinx.coroutines.flow.Flow

public class CustomRepository {

    companion object {
        private val TAG : String = CustomRepository::class.java.getSimpleName()
        private var database : CustomDatabase? = null
    }

    private val customDao : CustomDAO?

    constructor(context : Context) {
        if (database == null) database = provideRoomDatabase(
            context,
            "custom_database",
            provideRoomDatabaseCallback()
        )
        customDao = database?.customDao()
    }

    private fun provideRoomDatabase(context : Context, name : String, roomCallback : RoomDatabase.Callback) : CustomDatabase {
        return Room.databaseBuilder(
            context.getApplicationContext(),
            CustomDatabase::class.java,
            name
        )
        .fallbackToDestructiveMigration()
        .addCallback(roomCallback)
        .build()
    }

    private fun provideRoomDatabaseCallback() : RoomDatabase.Callback {
        return object : RoomDatabase.Callback() {
            override fun onCreate(db : SupportSQLiteDatabase) { //Initialize Database if no database attached to the App
                super.onCreate(db)
                /*Coroutines.io {
                    for (index in 0 until 500) {
                        insert(CustomEntity("name $index", R.drawable.ic_launcher_foreground))
                    }
                }*/
            }

            override fun onOpen(db : SupportSQLiteDatabase) { //Re-open Database if it has database attached to the App
                super.onOpen(db)
            }
        }
    }

    //region CRUD Operation
    public suspend fun insert(customEntity: CustomEntity) {
        customDao?.insert(
                customEntity
            )
    }

    public suspend fun update(customEntity: CustomEntity) {
        customDao?.update(
                customEntity
            )
    }

    public suspend fun delete(customEntity: CustomEntity) {
        println("${customEntity.id}")
        customDao?.delete(
                customEntity.id
            )
    }

    public suspend fun deleteAll() {
        customDao?.deleteAll()
    }

    public suspend fun getFlowPagingData() : Flow<PagingData<CustomEntity>> {
        return Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                CustomPagingSource(customDao)
            }
        ).flow
    }

    public suspend fun getFlow() : Flow<List<CustomEntity>>? {
        return customDao?.observeAll()
    }
    //endregion
    private suspend fun getPagingConfig() : PagingConfig {
        return PagingConfig(
            pageSize = 10,
            initialLoadSize = 10, //default: page size * 3
            prefetchDistance = 10, //default: page size
            //maxSize = PagedList.Config.MAX_SIZE_UNBOUNDED,
            enablePlaceholders = false //default: true
        )
    }
}