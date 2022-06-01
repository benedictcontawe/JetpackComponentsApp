package com.example.jetpackcomponentsapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jetpackcomponentsapp.util.Coroutines

@Database(
        entities = [CustomEntity::class],
        version = 1
)
abstract class CustomDatabase : RoomDatabase() {

    abstract fun customDao() : CustomDAO

    companion object {
        @Volatile private var instance : CustomDatabase? = null

        fun getInstance(context: Context): CustomDatabase? {
            if (instance == null) {
                synchronized(CustomDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CustomDatabase::class.java, "custom_database"
                    )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback : RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                //Initialize Database if no database attached to the App
                super.onCreate(db)
                //PopulateDbAsyncTask(instance).execute()
                Coroutines.io {
                    //instance?.customDao()?.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 0")))
                    //instance?.customDao()?.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 1")))
                    //instance?.customDao()?.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 2")))
                    //instance?.customDao()?.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 3")))
                    //instance?.customDao()?.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 4")))
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                //Re-open Database if it has database attached to the App
                super.onOpen(db)
            }
        }
    }
}