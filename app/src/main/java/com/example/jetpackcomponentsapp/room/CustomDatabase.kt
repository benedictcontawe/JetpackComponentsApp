package com.example.jetpackcomponentsapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CustomEntity::class), version = 1)
abstract class CustomDatabase : RoomDatabase {

    abstract fun CustomDao() : CustomDAO

    companion object {
        @Volatile private var instance : CustomDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {
            instance?:buildDatabase(context)
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                CustomDatabase::class.java, "custom_database")
                .build()
    }

    constructor()
}