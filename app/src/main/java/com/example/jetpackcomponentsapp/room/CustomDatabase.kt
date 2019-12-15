package com.example.jetpackcomponentsapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
        entities = [CustomEntity::class],
        version = 1
)
abstract class CustomDatabase : RoomDatabase {

    abstract fun CustomDao() : CustomDAO

    companion object {
        @Volatile private var instance : CustomDatabase? = null

        fun getInstance(context: Context): CustomDatabase? {
            //TODO: creating a instance of database has a run time error, FIX IT!
            if (instance == null) {
                synchronized(CustomDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            CustomDatabase::class.java, "custom_database.db")
                            .fallbackToDestructiveMigration()
                            //.addCallback()
                            .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }

    constructor()
}