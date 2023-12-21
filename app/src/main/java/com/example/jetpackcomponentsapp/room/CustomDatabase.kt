package com.example.jetpackcomponentsapp.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [CustomEntity::class],
    version = 1
)
abstract class CustomDatabase : RoomDatabase() {

    companion object {
        private val TAG : String = CustomDatabase::class.java.getSimpleName()
    }

    abstract fun customDao() : CustomDAO
}