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
        private val LOCK = Any()

        operator fun invoke(context: Context) : CustomDatabase = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                CustomDatabase::class.java, "custom_database")
                .build()
    }

    constructor()
}