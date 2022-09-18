package com.example.jetpackcomponentsapp.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CustomEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CustomDatabase : RoomDatabase() {

    companion object {
        private val TAG : String = CustomDatabase::class.java.getSimpleName()
    }

    abstract fun customDao() : CustomDAO
}