package com.example.jetpackcomponentsapp.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CustomDAO {

    @Insert
    fun insert(customEntity: CustomEntity)

    @Update
    fun update(customEntity: CustomEntity)

    @Delete
    fun delete(customEntity: CustomEntity)

    @Query("DELETE FROM custom_table")
    fun deleteAll()

    @Query("SELECT * FROM custom_table")
    fun getAll() : LiveData<List<CustomEntity>>
}