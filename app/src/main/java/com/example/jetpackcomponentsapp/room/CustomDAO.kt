package com.example.jetpackcomponentsapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

@Dao
interface CustomDAO {

    //@Insert
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(customEntity: CustomEntity)

    @Update
    fun update(customEntity: CustomEntity)

    //@Delete
    //fun delete(customEntity: CustomEntity)

    @Query("DELETE FROM custom_table WHERE Id = :id")
    fun delete(id : Int?)

    @Query("DELETE FROM custom_table")
    fun deleteAll()

    //@Query("SELECT * FROM custom_table")
    @Query("SELECT * FROM custom_table ORDER BY Id ASC")
    fun getAll() : LiveData<List<CustomEntity>>
}