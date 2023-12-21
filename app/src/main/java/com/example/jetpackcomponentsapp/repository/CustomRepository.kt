package com.example.jetpackcomponentsapp.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jetpackcomponentsapp.room.CustomDAO
import com.example.jetpackcomponentsapp.room.CustomDatabase
import com.example.jetpackcomponentsapp.room.CustomEntity

class CustomRepository : BaseRepository {

    companion object {
        private val TAG = CustomRepository::class.java.getSimpleName()
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
                /*
                Coroutines.io {
                    for (index in 0 until 500) {
                        insert(CustomEntity("name $index", R.drawable.ic_launcher_foreground))
                    }
                }
                */
            }

            override fun onOpen(db : SupportSQLiteDatabase) { //Re-open Database if it has database attached to the App
                super.onOpen(db)
            }
        }
    }
    //region CRUD Operation
    override public suspend fun insert(customEntity : CustomEntity) {
        customDao?.insert(
            customEntity
        )
    }

    override public suspend fun update(customEntity : CustomEntity) {
        customDao?.update(
            customEntity
        )
    }

    override public suspend fun delete(customEntity : CustomEntity) {
        println("${customEntity.id}")
        customDao?.delete(
            customEntity.id
        )
    }

    override public suspend fun deleteAll() {
        customDao?.deleteAll()
    }

    override public fun getAll() : LiveData<List<CustomEntity>>? {
        return customDao?.observeAll()
    }
    //endregion
    override public suspend fun onCLose() {
        if (database?.isOpen == true)
            database?.close()
    }
}